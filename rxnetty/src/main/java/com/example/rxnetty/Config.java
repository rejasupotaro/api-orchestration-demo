package com.example.rxnetty;

import com.example.rxnetty.app.Application;
import com.example.rxnetty.app.RxNettyApplication;
import com.example.rxnetty.exceptions.RouteNotFoundException;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.RequestHandler;

public final class Config {
    private static final int PORT = 8080;
    private static final RxNettyApplication APPLICATION = new Application();

    public static void main(String... args) throws InterruptedException {
        APPLICATION.run();
        APPLICATION.afterInitialized();

        HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(PORT, (request, response) -> {
            try {
                RequestHandler<ByteBuf, ByteBuf> route = null;
                route = APPLICATION.match(request);
                return route.handle(request, response);
            } catch (RouteNotFoundException e) {
                return APPLICATION.routeMissing(request, response);
            }
        });

        server.startAndWait();
    }
}
