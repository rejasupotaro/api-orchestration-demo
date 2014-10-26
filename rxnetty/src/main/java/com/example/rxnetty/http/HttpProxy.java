package com.example.rxnetty.http;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;

public class HttpProxy {
    private HttpServerRequest<ByteBuf> req;
    private HttpServerResponse<ByteBuf> res;

    public HttpProxy(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> res) {
        this.req = req;
        this.res = res;
    }

    public Observable<HttpClientResponse<ByteBuf>> get(String uri) {
        String authorization = req.getHeaders().getHeader("Authorization");
        HttpClientRequest httpClientRequest = HttpClientRequest.createGet(uri);
        httpClientRequest.withHeader("Authorization", authorization);
        return RxNetty.createHttpRequest(httpClientRequest);
    }
}
