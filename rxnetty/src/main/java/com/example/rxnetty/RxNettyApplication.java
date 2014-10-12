package com.example.rxnetty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class RxNettyApplication {
    private Map<String, RequestHandler<ByteBuf, ByteBuf>> routes = new HashMap<>();

    protected Observable<Void> close(HttpServerResponse res, String body) {
        res.setStatus(HttpResponseStatus.OK);
        res.writeString(body);
        return res.close();
    }

    public void get(String path, RequestHandler<ByteBuf, ByteBuf> handler) {
        routes.put(path, handler);
    }

    public RequestHandler<ByteBuf, ByteBuf> match(String uri) {
        RequestHandler<ByteBuf, ByteBuf> handler = routes.get(uri);
        if (handler == null) {
            throw new RuntimeException(uri + " is not found");
        }
        return handler;
    }
}
