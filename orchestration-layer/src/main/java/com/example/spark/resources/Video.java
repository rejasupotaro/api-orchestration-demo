package com.example.spark.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Video extends Resource {
    @JsonProperty("id") private String id;
    @JsonProperty("title") private String title;
}
