package com.example.spark.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookmarkTag {
    @JsonProperty("id") private String id;
    @JsonProperty("title") private String title;
}
