package com.example.spark;

import com.example.spark.resources.BookmarkTag;
import com.example.spark.resources.Recipe;
import com.example.spark.resources.Video;
import com.example.spark.services.BookmarkTagService;
import com.example.spark.services.RecipeService;
import com.example.spark.services.VideoService;
import rx.Observable;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class App implements SparkApplication {
    @Override
    public void init() {
        get("/", (req, res) -> {
            String[] data = new String[]{"a", "b", "c"};
            Observable.from(data)
                    .map((n) -> {
                        return n;
                    })
                    .scan((a, b) -> {
                        return a + b;
                    })
                    .toBlocking().forEach(res::body);

            return res.body();
        });

        get("/recipes/:id", (req, res) -> {
            String recipeId = req.params(":id");

            Observable<Recipe> recipe = RecipeService.get(recipeId);
            recipe.subscribe(r -> {
                Observable<Video> videos = VideoService.get(r.getVideoId());
                Observable<BookmarkTag> bookmarkTags = BookmarkTagService.get(r.getVideoId());

                Observable.merge(videos, bookmarkTags)
                        .subscribe(subscriber -> {
                            System.out.println(subscriber.toString());
                        }, exception -> {
                            System.out.println(exception.toString());
                        });
            });

            return "JAVA";
        });
    }
}
