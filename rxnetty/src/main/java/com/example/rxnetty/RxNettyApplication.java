package com.example.rxnetty;

import com.example.rxnetty.exceptions.RouteNotFoundException;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.util.HashMap;

public abstract class RxNettyApplication {
    private HashMap<String, HashMap<HttpMethod, RequestHandler<ByteBuf, ByteBuf>>> routes = new HashMap<>();

    protected Observable<Void> close(HttpServerResponse res, String body) {
        res.setStatus(HttpResponseStatus.OK);
        res.writeString(body);
        return res.close();
    }

    public void get(String path, RequestHandler<ByteBuf, ByteBuf> handler) {
        addRoute(HttpMethod.GET, path, handler);
    }

    private void addRoute(HttpMethod method, String path, RequestHandler<ByteBuf, ByteBuf> handler) {
        if (routes.containsKey(path)) {
            HashMap<HttpMethod, RequestHandler<ByteBuf, ByteBuf>> methodHandlerMap = routes.get(path);
            methodHandlerMap.put(method, handler);
        } else {
            routes.put(path,
                    new HashMap<HttpMethod, RequestHandler<ByteBuf, ByteBuf>>() {{
                        put(method, handler);
                    }});
        }
    }

    public Observable<Void> routeMissing(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> res) {
        throw new RuntimeException("routeMissing is not implemented");
    }

    public void showRoutes() {
        for (String path : routes.keySet()) {
            for (HttpMethod method : routes.get(path).keySet()) {
                System.out.println(method.name() + " " + path);
            }
        }
    }

    public RequestHandler<ByteBuf, ByteBuf> match(HttpServerRequest<ByteBuf> req) throws RouteNotFoundException {
        String method = req.getHttpMethod().toString();
        String path = req.getPath();

        HashMap<HttpMethod, RequestHandler<ByteBuf, ByteBuf>> methodHandlerMap = routes.get(path);
        if (methodHandlerMap == null) {
            throw new RouteNotFoundException(method + " " + path + " is not found");
        }
        RequestHandler<ByteBuf, ByteBuf> handler = methodHandlerMap.get(req.getHttpMethod());
        if (handler == null) {
            throw new RouteNotFoundException(method + " " + path + " is not found");
        }
        return handler;
    }

    public abstract void run();
    public abstract void afterInitialized();
}
