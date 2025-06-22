package com.example.app_foodstore.APIService.Client;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.Constant.ConstantVariable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenRefreshClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantVariable.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
