package com.example.smarthome.Network;

import com.example.smarthome.Network.interceptors.AuthorizationInterceptor;
import com.example.smarthome.Network.interceptors.ConnectivityInterceptor;
import com.example.smarthome.Network.interceptors.JWTInterceptor;
import com.example.smarthome.constants.Urls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizedService {
    private static AuthorizedService mInstance;
    private static final String BASE_URL = Urls.BASE_URL;
    //private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private Retrofit mRetrofit;

    private AuthorizedService() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ConnectivityInterceptor())
                .addInterceptor(new AuthorizationInterceptor())
                .addInterceptor(new JWTInterceptor());

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }
    public static AuthorizedService getInstance() {
        if (mInstance == null) {
            mInstance = new AuthorizedService();
        }
        return mInstance;
    }
    public FastDostavka getJSONApi() {
        return mRetrofit.create(FastDostavka.class);
    }
}