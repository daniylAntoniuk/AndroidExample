package com.example.smarthome.Network.interceptors;

import com.example.smarthome.account.JwtServiceHolder;
import com.example.smarthome.application.HomeApplication;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JWTInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HomeApplication context = (HomeApplication)HomeApplication.getAppContext();
        JwtServiceHolder jwtService = (JwtServiceHolder)context.getCurrentActivity();
        String token = jwtService.getToken().getToken();
        if(token != null && !token.isEmpty())
        {
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer "+ token)
                    .build();
            return chain.proceed(newRequest);
        }

        Request newRequest = originalRequest.newBuilder()
                .build();
        return chain.proceed(newRequest);
    }
}
