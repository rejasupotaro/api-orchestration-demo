package com.example.rxnetty;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.RequestHandler;

public final class Config {
    private static final int PORT = 8080;
    private static final Application APPLICATION = new Application();

    public static void main(String... args) throws InterruptedException {
        APPLICATION.run();
        APPLICATION.afterInitialized();

        HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(PORT, (request, response) -> {
            RequestHandler<ByteBuf, ByteBuf> route = APPLICATION.match(request.getPath());
            return route.handle(request, response);
        });

        server.startAndWait();
    }
}
