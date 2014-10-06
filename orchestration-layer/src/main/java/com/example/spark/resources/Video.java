package com.example.spark.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import rx.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Video {
    @JsonProperty("id") private String id;
    @JsonProperty("title") private String title;
}
