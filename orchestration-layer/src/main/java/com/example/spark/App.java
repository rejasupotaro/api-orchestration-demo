package com.example.spark;

import com.example.spark.resources.BookmarkTag;
import com.example.spark.resources.Resource;
import com.example.spark.resources.Video;
import com.example.spark.services.BookmarkTagService;
import com.example.spark.services.RecipeService;
import com.example.spark.services.VideoService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class App implements SparkApplication {
    @Override
    public void init() {
        get("/bookmark_tags/:ids", (req, res) -> {
            String[] ids = req.params(":ids").split(",");
            return BookmarkTagService.batchDelete(ids)
                    .map(Resource::toJson)
                    .reduce(new JSONArray(), (a, b) -> a.put(b))
                    .toBlocking().first();
        });

        get("/recipes/:id", (req, res) -> {
            res.type("application/json");
            String recipeId = req.params(":id");
            return RecipeService.get(recipeId)
                    .map(r -> {
                        Observable<Video> videos = VideoService.get(r.getVideoId());
                        Observable<BookmarkTag> bookmarkTags = BookmarkTagService.get(r.getVideoId());
                        return Observable.zip(videos, bookmarkTags, (v, b) -> {
                            try {
                                JSONObject json = new JSONObject();
                                json.put("video", v.toJson());
                                json.put("bookmark_tag", b.toJson());
                                return json;
                            } catch (JSONException e) {
                                return new JSONObject();
                            }
                        }).reduce(new JSONArray(), (a, b) -> {
                            a.put(b);
                            return a;
                        }).toBlocking().first();
                    }).toBlocking().first();
        });
    }

    private void p(String s) {
        System.out.println(s);
    }
}
