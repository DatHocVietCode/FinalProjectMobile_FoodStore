package com.example.app_foodstore.APIService.Client;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.Constant.ConstantVariable;
import com.example.app_foodstore.Interceptor.AuthInterceptor;
import com.example.app_foodstore.Manager.TokenManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {

            // Tạo SharedPreferences
            SharedPreferences prefs = context.getSharedPreferences(ConstantVariable.SHARED_PREFS_NAME, Context.MODE_PRIVATE);

            // Tạo TokenManager
            TokenManager tokenManager = new TokenManager(prefs, APIClient.getAuthService());

            // Tạo AuthInterceptor
            AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);

            // Tạo OkHttpClient
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantVariable.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}