package com.example.spark;

import com.example.spark.resources.Recipe;
import com.example.spark.resources.Video;
import com.example.spark.services.VideoService;
import rx.Observable;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class App implements SparkApplication {
    @Override
    public void init() {
        get("/", (req, res) -> {
            final String[] body = {""};

            String[] data = new String[]{"a", "b", "c"};
            Observable.from(data)
                    .map((n) -> {
                        return n;
                    })
                    .scan((a, b) -> {
                        return a + b;
                    })
                    .toBlocking().forEach(r -> {
                        System.out.println(r);
                        body[0] = r;
                    }
            );

            System.out.println(body[0]);
            return body[0];
        });

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
