package com.example.app_foodstore.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constant {
    // Dùng cho gia3 lập
//    public static final String BASE_URL = "http://10.0.2.2:8081/";
    // Dùng cho thiết bị thật chung mạng
    public static final String BASE_URL = "http://172.20.10.3:8080/";
    // Render
    //public static final String BASE_URL = "https://backend-foodapp-latest.onrender.com/";
    public static  final String IMG_URL = BASE_URL + "uploads/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
