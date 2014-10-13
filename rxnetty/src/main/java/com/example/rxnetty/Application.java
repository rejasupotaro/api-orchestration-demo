package com.example.rxnetty;

import org.json.JSONArray;
import rx.Observable;

import java.nio.charset.Charset;

public class Application extends RxNettyApplication {
    @Override
    public void run() {
        get("/users", (req, res) -> {
            String[] ids = getParamAsArray(req, "ids");

            String body = Observable.from(ids)
                    .flatMap(n -> HttpProxy.get("http://localhost:3000/users/" + n))
                    .flatMap(r -> r.getContent())
                    .reduce(new JSONArray(), (a, b) -> {
                        a.put(b.toString(Charset.defaultCharset()));
                        return a;
                    })
                    .map(JSONArray::toString)
                    .toBlocking().first();

            return close(res, body);
        });
    }

    @Override
    public void afterInitialized() {
        showRoutes();
    }
}
