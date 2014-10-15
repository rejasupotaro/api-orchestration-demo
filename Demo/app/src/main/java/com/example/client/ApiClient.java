package com.example.client;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorNotImplementedException;

public class ApiClient {
    private static final OkHttpClient client = new OkHttpClient();

    public static Observable<String> createGetRequest(final String url) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(get(url));
                } catch (IOException | JSONException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static String get(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();
        return body.startsWith("{")
                ? new JSONObject(body).toString(4)
                : new JSONArray(body).toString(4);
    }
}
