package com.example.rxnetty.http;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.Observable;

public class HttpProxy {
    public static Observable<HttpClientResponse<ByteBuf>> get(String uri) {
        return RxNetty.createHttpGet(uri);
    }
}
