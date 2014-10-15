package com.example.rxnetty.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static String get(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            return "";
        }
    }
}
