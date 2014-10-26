package com.example.client.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static String format(String str) {
        try {
            if (str.startsWith("{")) {
                return new JSONObject(str).toString(4);
            } else if (str.startsWith("[")) {
                return new JSONArray(str).toString(4);
            } else {
                return str;
            }
        } catch (JSONException e) {
            return str;
        }
    }

    public static String serialize(List<String> inputHistories) {
        StringBuilder builder = new StringBuilder();
        for (String text : inputHistories) {
            builder.append(text).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public static List<String> deserialize(String serializedText) {
        return Arrays.asList(serializedText.split(","));
    }
}
