package com.example.client.services;

import com.example.client.oauth.AuthToken;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ServiceProvider {
    private static final String AUTH_ENDPOINT = "http://localhost:3000";
    private static final OkClient CLIENT = new OkClient(new OkHttpClient());
    private static AuthToken authToken = new AuthToken();

    public static <T> Observable<T> get(final Class<T> serviceClass, final String baseUrl) {
        if (authToken == null) {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(final Subscriber<? super T> subscriber) {
                    JSONObject json = new JSONObject();
                    getAuthService().get(json)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<AuthToken>() {
                                @Override
                                public void call(AuthToken authToken) {
                                    subscriber.onNext(getService(serviceClass, baseUrl));
                                }
                            });
                }
            });
        } else {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    subscriber.onNext(getService(serviceClass, baseUrl));
                }
            });
        }
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
