package com.example.spark.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource extends Resource {
    @JsonProperty("id") private int id;
    @JsonProperty("url") private String url;
    @JsonProperty("name") private String name;

    public UserResource() {}

    public UserResource(int id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public static final UserResource DUMMY_USER = new UserResource(
            1,
            "http://twitter.com/rejasupotaro",
            "rejasupotaro");
}

