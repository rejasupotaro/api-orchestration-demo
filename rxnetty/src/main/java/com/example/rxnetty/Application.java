package com.example.rxnetty;

import java.nio.charset.Charset;

public class Application extends RxNettyApplication {
    public void run() {
        get("/", (req, res) -> {
            String body = HttpProxy.get("/users/1")
                    .flatMap(response -> response.getContent())
                    .map(data -> "Client => " + data.toString(Charset.defaultCharset()))
                    .toBlocking().first();

            return close(res, body);
        });
    }
}
