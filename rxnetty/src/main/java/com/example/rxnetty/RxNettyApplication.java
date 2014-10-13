package com.example.rxnetty;

import com.example.rxnetty.exceptions.RouteNotFoundException;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public abstract class RxNettyApplication {
    private Map<String, RequestHandler<ByteBuf, ByteBuf>> routes = new HashMap<>();

    protected Observable<Void> close(HttpServerResponse res, String body) {
        res.setStatus(HttpResponseStatus.OK);
        res.writeString(body);
        return res.close();
    }

    public void get(String path, RequestHandler<ByteBuf, ByteBuf> handler) {
        routes.put(path, handler);
    }

    public Observable<Void> routeMissing(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> res) {
        throw new RuntimeException("routeMissing is not implemented");
    }

    public void showRoutes() {
        routes.keySet().forEach(System.out::println);
    }

    public RequestHandler<ByteBuf, ByteBuf> match(String uri) throws RouteNotFoundException {
        RequestHandler<ByteBuf, ByteBuf> handler = routes.get(uri);
        if (handler == null) {
            throw new RouteNotFoundException(uri + " is not found");
        }
        return handler;
    }

    public abstract void run();
    public abstract void afterInitialized();
}
