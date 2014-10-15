package com.example.rxnetty;

import com.example.rxnetty.utils.JsonUtils;
import com.example.rxnetty.utils.ResponseTransformer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;

public class Application extends RxNettyApplication {
    @Override
    public void run() {
        get("/users", (req, res) -> {
            String[] ids = getParamAsArray(req, "ids");

            String body = Observable.from(ids)
                    .flatMap(s -> HttpProxy.get("http://localhost:3000/users/" + s))
                    .flatMap(r -> r.getContent())
                    .map(ResponseTransformer::toJson)
                    .reduce(new JSONArray(), (t1, t2) -> {
                        t1.put(t2);
                        return t1;
                    })
                    .map(JSONArray::toString)
                    .toBlocking().first();

            return close(res, body);
        });

        get("/top_page", (req, res) -> {
            String recipeId = getParam(req, "recipe_id");
            String bookmarkTagId = getParam(req, "video_id");
            String videoId = getParam(req, "video_id");

            Observable<JSONObject> recipe = HttpProxy.get("http://localhost:3000/recipes/" + recipeId)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);
            Observable<JSONObject> bookmarkTag = HttpProxy.get("http://localhost:3000/bookmark_tags/" + bookmarkTagId)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);
            Observable<JSONObject> video = HttpProxy.get("http://localhost:3000/videos/" + videoId)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);

            String body = Observable.zip(recipe, bookmarkTag, video, (r, b, v) -> {
                JSONObject json = new JSONObject();
                try {
                    json.put("recipe", r);
                    json.put("bookmark_tag", v);
                    json.put("video", v);
                } catch (JSONException e) {
                    // ignore
                }
                return json;
            }).map(JSONObject::toString).toBlocking().first();

            return close(res, body);
        });

        get("/recipe", (req, res) -> {
            String id = getParam(req, "id");

            Observable<JSONObject> recipe = HttpProxy.get("http://localhost:3000/users/" + id)
                    .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);

            String body = recipe
                    .flatMap(json -> {
                        String bookmarkTagId = JsonUtils.get(json, "bookmark_tag_id");
                        String videoId = JsonUtils.get(json, "video_id");

                        Observable<JSONObject> bookmarkTag = HttpProxy.get("http://localhost:3000/bookmark_tags/" + bookmarkTagId)
                                .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);
                        Observable<JSONObject> video = HttpProxy.get("http://localhost:3000/videos/" + videoId)
                                .flatMap(r -> r.getContent()).map(ResponseTransformer::toJson);

                        return Observable.zip(bookmarkTag, video, (b, v) -> {
                            try {
                                json.put("bookmark_tag", b);
                                json.put("video", v);
                            } catch (JSONException e) {
                                // ignore
                            }
                            return json;
                        }).map(JSONObject::toString);
                    }).toBlocking().first();

            return close(res, body);
        });
    }

    @Override
    public void afterInitialized() {
        showRoutes();
    }
}
