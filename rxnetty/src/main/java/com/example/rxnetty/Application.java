package com.example.rxnetty;

import com.example.rxnetty.utils.ResponseTransformer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;

public class Application extends RxNettyApplication {
    @Override
    public void run() {
        get("/users", (req, res) -> {
            String[] ids = getParamAsArray(req, "ids");

            String body = Observable.from(ids)
                    .flatMap(s -> HttpProxy.get("http://localhost:3000/users/" + s))
                    .flatMap(r -> r.getContent())
                    .map(ResponseTransformer::toJson)
                    .reduce(new JSONArray(), (t1, t2) -> {
                        t1.put(t2);
                        return t1;
                    })
                    .map(JSONArray::toString)
                    .toBlocking().first();

            return close(res, body);
        });

        get("/recipes", (req, res) -> {
            String id = getParam(req, "id");

            Observable<JSONObject> recipe = HttpProxy.get("http://localhost:3000/users/" + id)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);
            Observable<JSONObject> bookmarkTag = HttpProxy.get("http://localhost:3000/users/" + id)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);
            Observable<JSONObject> video = HttpProxy.get("http://localhost:3000/users/" + id)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);

            String body = Observable.zip(recipe, bookmarkTag, video, (r, b, v) -> {
                try {
                    r.put("bookmark_tag", b);
                    r.put("video", v);
                } catch (JSONException e) {
                    // ignore
                }
                return r;
            }).map(JSONObject::toString).toBlocking().first();

            return close(res, body);
        });
    }

    @Override
    public void afterInitialized() {
        showRoutes();
    }
}
