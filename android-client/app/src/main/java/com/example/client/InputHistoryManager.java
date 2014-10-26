package com.example.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.client.utils.StringUtils;

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
                String serializedInputHistories = prefs.getString(PREFS_KEY_INPUT_HISTORIES, "");
                List<String> inputHistories = StringUtils.deserialize(serializedInputHistories);
                subscriber.onNext(inputHistories);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void write(Context context, List<String> inputHistories) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString(PREFS_KEY_INPUT_HISTORIES, StringUtils.serialize(inputHistories));
        editor.apply();
    }

    public static void clear(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PREFS_KEY_INPUT_HISTORIES);
        editor.apply();
    }
}
