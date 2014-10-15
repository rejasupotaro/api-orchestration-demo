package com.example.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InputHistoryManager {
    private static final String PREFS_KEY_INPUT_HISTORIES = "input_histories";

    public static Observable<List<String>> read(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                Log.e("debugging", "call");
                List<String> inputHistories = deserialize(prefs.getString(PREFS_KEY_INPUT_HISTORIES, ""));
                subscriber.onNext(inputHistories);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void write(Context context, List<String> inputHistories) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_KEY_INPUT_HISTORIES, serialize(inputHistories));
        editor.apply();
    }

    private static String serialize(List<String> inputHistories) {
        StringBuilder builder = new StringBuilder();
        for (String text : inputHistories) {
            builder.append(text).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private static List<String> deserialize(String serializedText) {
        return Arrays.asList(serializedText.split(","));
    }
}
