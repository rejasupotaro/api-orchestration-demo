package com.example.client.services;

import com.example.client.models.User;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface UserService {
    @GET("/users/{user}")
    Observable<User> get(@Path("user") int id);
}
