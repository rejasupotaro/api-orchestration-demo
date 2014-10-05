package com.example.spark;

import static spark.Spark.*;

import com.example.spark.resources.Recipe;
import com.example.spark.resources.UserResource;
import com.example.spark.resources.Video;
import com.example.spark.services.VideoService;
import rx.Observable;
import rx.functions.Action1;
import spark.servlet.SparkApplication;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class App implements SparkApplication {
    @Override
    public void init() {
        get("/recipe_detail/:id", (req, res) -> {
//            String recipeId = req.params(":id");
//            Observable<Recipe> recipe = getRecipe(recipeId);
//            recipe.map(r -> {
//                Observable<Video> video = VideoService.getVideos(r.getVideoId());
//
//                Observable.merge(video)
//                        .subscribe(element -> {
//                            System.out.println(element.toString());
//                        }, exception -> {
//                            System.out.println(exception.toString());
//                        });
//            });
            return "";
        });
    }

    private Observable<Video> getVideo(String recipeId) {
        return VideoService.getVideos(recipeId);
    }


    private Observable<Recipe> getRecipe(String recipeId) {
        return Observable.create(observer -> {
            Recipe recipe = getRecipeFromNetwork(recipeId);
            observer.onNext(recipe);
            observer.onCompleted();
        });
    }

    private Recipe getRecipeFromNetwork(String id) {
        return new Recipe();
    }
}
