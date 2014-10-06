package com.example.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ObjectMapperHelper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T read(Response res, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(res.body().string(), clazz);
    }
}
