package com.example.rxnetty;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.Observable;

public class HttpProxy {
    public static Observable<HttpClientResponse<ByteBuf>> get(String path) {
        return RxNetty.createHttpGet(Config.PROXY_HOST + path);
    }
}
