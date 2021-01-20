package com.example.smarthome.Network.interceptors;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.smarthome.BaseActivity;
import com.example.smarthome.MainActivity;
import com.example.smarthome.MenuActivity;
import com.example.smarthome.Network.NetworkService;
import com.example.smarthome.Network.Refresh;
import com.example.smarthome.Network.Tokens;
import com.example.smarthome.application.HomeApplication;


import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .build();
        Response response = chain.proceed(newRequest);

        if (response.code() == 401) {
            HomeApplication context = (HomeApplication) HomeApplication.getAppContext();
            BaseActivity a = (BaseActivity) context.getCurrentActivity();
            final BaseActivity act = (BaseActivity) context.getCurrentActivity();
            NetworkService.getInstance()
                    .getJSONApi()
                    .refresh(new Refresh(a.getToken().getToken(),a.getToken().getRefreshToken()))
                    .enqueue(new Callback<Tokens>() {
                        @Override
                        public void onResponse(@NonNull Call<Tokens> call, @NonNull retrofit2.Response<Tokens> response) {
                            if (response.errorBody() == null && response.isSuccessful()) {
                                Tokens post = response.body();
                                act.saveJWTToken(post.getToken(),post.getRefreshToken());
                                Log.i("REFRESH",post.getRefreshToken());
                            } else {
                                act.removeToken();
                                //err
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<Tokens> call, @NonNull Throwable t) {
                            act.removeToken();
                            //textView.append("Error occurred while getting request!");
                            t.printStackTrace();
                        }
                    });
//            BiometricPrompt.PromptInfo promptI = new BiometricPrompt.PromptInfo.Builder()
//                    .setTitle("Підтверження входу в додаток")
//                    .setSubtitle("Підтвердіть ваші біометричні дані")
//                    .setDeviceCredentialAllowed(true)
//                    // Can't call setNegativeButtonText() and
//                    // setAllowedAuthenticators(...|DEVICE_CREDENTIAL) at the same time.
//                    // .setNegativeButtonText("Use account password")
//                    .build();
//            Executor executor = ContextCompat.getMainExecutor(context);
//            BiometricPrompt bp = new BiometricPrompt(a, executor, new BiometricPrompt.AuthenticationCallback() {
//                @Override
//                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                    Toast.makeText(HomeApplication.getAppContext(),
//                            "Authentication error", Toast.LENGTH_SHORT)
//                            .show();
//                }
//
//                @Override
//                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
//                    Toast.makeText(HomeApplication.getAppContext(),
//                            "Authentication succeeded!", Toast.LENGTH_SHORT)
//                            .show();
//                }
//
//                @Override
//                public void onAuthenticationFailed() {
//                    Toast.makeText(HomeApplication.getAppContext(), "Authentication failed",
//                            Toast.LENGTH_SHORT)
//                            .show();
//                }
//            });
//            bp.authenticate(promptI);

//            NavigationHost navigationHost = (NavigationHost) context.getCurrentActivity();
//            navigationHost.navigateTo(new LoginFragment(), false);

            //  return response;
        }
        return response;
    }
}
