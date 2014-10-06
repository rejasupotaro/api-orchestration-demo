package com.example.spark.services;

import com.example.spark.resources.BookmarkTag;
import rx.Observable;

public class BookmarkTagService {
    public static Observable<BookmarkTag> get(String id) {
        return Observable.create(observer -> {
            BookmarkTag recipe = new BookmarkTag();
            observer.onNext(recipe);
            observer.onCompleted();
        });
    }
}
