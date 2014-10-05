package com.example.spark.services;

import com.example.spark.resources.Video;
import rx.Observable;

public class VideoService {
    public static Observable<Video> getVideos(String recipeId) {
        return Observable.create(observer -> {
            Video video = getVideoFromNetwork(recipeId);
            observer.onNext(video);
            observer.onCompleted();
        });
    }

    private static Video getVideoFromNetwork(String id) {
        return new Video();
    }
}
