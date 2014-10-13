package com.example.rxnetty.utils;

import io.netty.buffer.ByteBuf;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;

public class ResponseUtils {
    public static JSONObject toJson(ByteBuf b) {
        try {
            return new JSONObject(b.toString(Charset.defaultCharset()));
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
