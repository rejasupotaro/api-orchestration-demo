package com.example.rxnetty.utils;

import io.netty.buffer.ByteBuf;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;

public class ResponseTransformer {
    public static JSONObject toJson(ByteBuf b) {
        if (b.refCnt() == 0) {
            return new JSONObject();
        }
        try {
            String json = b.toString(Charset.defaultCharset());
            System.out.println("b: " + json);
            return new JSONObject(json);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
