package com.example.rxnetty;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.RequestHandler;

public final class Config {
    private static final Application APPLICATION = new Application();

    public static void main(String... args) throws InterruptedException {
        APPLICATION.run();

        HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8080, (request, response) -> {
            RequestHandler<ByteBuf, ByteBuf> route = APPLICATION.match(request.getPath());
            return route.handle(request, response);
        });

        server.startAndWait();
    }
}
