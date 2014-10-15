package com.example.rxnetty.utils;

import io.netty.buffer.ByteBuf;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;

public class ResponseTransformer {
    public static JSONObject toJson(ByteBuf b) {
        try {
            String json = b.toString(Charset.defaultCharset());
            return new JSONObject(json);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
