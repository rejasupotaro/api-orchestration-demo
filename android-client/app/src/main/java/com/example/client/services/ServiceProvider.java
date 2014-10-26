package com.example.client.services;

import com.example.client.oauth.AuthToken;
import com.example.client.utils.StringUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

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
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final OkClient OK_CLIENT = new OkClient(OK_HTTP_CLIENT);
    private static AuthToken authToken = new AuthToken();

    public static <T> Observable<T> get(final Class<T> serviceClass, final String baseUrl) {
        if (authToken == null) {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(final Subscriber<? super T> subscriber) {
                    JSONObject json = new JSONObject();
                    createAuthService().get(json)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<AuthToken>() {
                                @Override
                                public void call(AuthToken authToken) {
                                    subscriber.onNext(createService(serviceClass, baseUrl));
                                }
                            });
                }
            });
        } else {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    subscriber.onNext(createService(serviceClass, baseUrl));
                }
            });
        }
    }

    private static <T> T createService(Class<T> serviceClass, String baseUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(OK_CLIENT);
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                String authorization = "Bearer " + authToken.getAccessToken();
                request.addHeader("Authorization", authorization);
            }
        });
        RestAdapter restAdapter = builder.build();
        return restAdapter.create(serviceClass);
    }

    private static AuthService createAuthService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(AUTH_ENDPOINT)
                .setClient(OK_CLIENT)
                .build();
        return restAdapter.create(AuthService.class);
    }

    public static Observable<String> createGetRequest(final String url) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String authorization = "Bearer " + authToken.getAccessToken();
                    Request request = new Request.Builder()
                            .header("Authorization", authorization)
                            .url(url)
                            .build();
                    Response response = OK_HTTP_CLIENT.newCall(request).execute();

                    subscriber.onNext(StringUtils.format(response.body().string()));
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
