package com.example.spark.services;

import com.example.spark.ObjectMapperHelper;
import com.example.spark.resources.Video;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import rx.Observable;

import java.io.IOException;

public class VideoService {
    public static Observable<Video> getVideos(String recipeId) {
        return Observable.create(observer -> {
            Video video = getVideoFromNetwork(recipeId);
            observer.onNext(video);
            observer.onCompleted();
        });
    }

    private static Video getVideoFromNetwork(String id) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:3000")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return ObjectMapperHelper.read(response, Video.class);
        } catch (IOException e) {
            return null;
        }
    }
}
