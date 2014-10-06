package com.example.spark.services;

import com.example.spark.resources.Recipe;
import rx.Observable;

public class RecipeService {
    public static Observable<Recipe> get(String id) {
        return Observable.create(observer -> {
            Recipe recipe = new Recipe();
            observer.onNext(recipe);
            observer.onCompleted();
        });
    }
}
