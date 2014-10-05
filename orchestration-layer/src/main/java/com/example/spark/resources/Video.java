package com.example.spark.resources;

import rx.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Video {
    public Observable<Metadata> getMetadata() {
        return Observable.create(observer -> {

        });
    }

    public static class Metadata extends HashMap<String, String> {
    }
}
