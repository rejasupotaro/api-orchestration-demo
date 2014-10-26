package com.example.client.services;

import com.example.client.oauth.AuthToken;

import org.json.JSONObject;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface AuthService {
    @POST("/oauth/token")
    Observable<AuthToken> get(@Body JSONObject body);
}
