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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static String get(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return format(response.body().string());
    }

    private static String format(String str) throws JSONException {
        if (str.startsWith("{")) {
            return new JSONObject(str).toString(4);
        } else if (str.startsWith("[")) {
            return new JSONArray(str).toString(4);
        } else {
            return str;
        }
    }
}
