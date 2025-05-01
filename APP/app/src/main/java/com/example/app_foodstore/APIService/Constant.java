package com.example.app_foodstore.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constant {
    // Dùng cho gia3 lập
    //public static final String BASE_URL = "http://10.0.2.2:8081/";
    // Dùng cho thiết bị thật chung mạng
    public static final String BASE_URL = "http://192.168.1.23:8081/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
