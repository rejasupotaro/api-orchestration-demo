package com.example.rxnetty;

import java.nio.charset.Charset;

public class Application extends RxNettyApplication {
    public void run() {
        get("/", (req, res) -> {
            String body = HttpProxy.get("http://localhost.com:3000/users/1")
                    .flatMap(r -> r.getContent())
                    .map(b -> "Client => " + b.toString(Charset.defaultCharset()))
                    .toBlocking().first();

            return close(res, body);
        });
    }
}
