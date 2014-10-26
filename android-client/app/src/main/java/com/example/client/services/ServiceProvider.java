package com.example.client.services;

import com.example.client.oauth.AuthToken;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.Subscriber;

public class ServiceProvider {
    private static final String AUTH_ENDPOINT = "http://localhost:3000";
    private static final OkClient CLIENT = new OkClient(new OkHttpClient());
    private static AuthToken authToken = new AuthToken();

    public static <T> Observable<T> get(final Class<T> serviceClass, final String baseUrl) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(getService(serviceClass, baseUrl));
            }
        });
    }

    private static <T> T getService(Class<T> serviceClass, String baseUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(CLIENT);

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                String authorization = "Bearer " + authToken.getAccessToken();
                request.addHeader("Authorization", authorization);
            }
        });

        return builder.build().create(serviceClass);
    }

    private static AuthService getAuthService() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(AUTH_ENDPOINT)
                .setClient(CLIENT);
        return builder.build().create(AuthService.class);
    }
}
