package com.example.client.services;

import com.example.client.models.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface UserService {
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String id);

    @GET("/users")
    Observable<List<User>> getUsers(@Query("ids") String ids);
}
