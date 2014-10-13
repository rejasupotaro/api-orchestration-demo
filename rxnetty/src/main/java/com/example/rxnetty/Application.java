package com.example.rxnetty;

import com.example.rxnetty.utils.ResponseUtils;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.json.JSONArray;
import org.json.JSONException;
import rx.Observable;

public class Application extends RxNettyApplication {
    @Override
    public void run() {
        get("/users", (req, res) -> {
            String[] ids = getParamAsArray(req, "ids");

            String body = Observable.from(ids)
                    .flatMap(n -> HttpProxy.get("http://localhost:3000/users/" + n))
                    .flatMap(r -> r.getContent())
                    .map(ResponseUtils::toJson)
                    .reduce(new JSONArray(), (a, b) -> {
                        a.put(b);
                        return a;
                    })
                    .map(JSONArray::toString)
                    .toBlocking().first();

            return close(res, body);
        });

        get("/recipes", (req, res) -> {
            String id = getParam(req, "id");
            String body = HttpProxy.get("http://localhost:3000/recipes/" + id)
                    .flatMap(r -> r.getContent())
                    .map(ResponseUtils::toJson)
                    .flatMap(b -> {
                        Observable<HttpClientResponse<ByteBuf>> videos = HttpProxy.get("http://localhost:3000/recipes/" + id);
                        Observable<HttpClientResponse<ByteBuf>> bookmarkTags = HttpProxy.get("http://localhost:3000/recipes/" + id);

                        return Observable.zip(videos, bookmarkTags, (vs, bs) -> {
                            try {
                                b.put("video", vs.getContent()
                                        .map(ResponseUtils::toJson)
                                        .toBlocking().first());
                                b.put("bookmark_tag", bs.getContent()
                                        .map(ResponseUtils::toJson)
                                        .toBlocking().first());
                            } catch (JSONException e) {
                                // ignore
                            }
                            return b;
                        })
                                .reduce(new JSONArray(), (l, r) -> l.put(r))
                                .map(JSONArray::toString);
                    }).toBlocking().first();

            return close(res, body);
        });
    }

    @Override
    public void afterInitialized() {
        showRoutes();
    }
}
