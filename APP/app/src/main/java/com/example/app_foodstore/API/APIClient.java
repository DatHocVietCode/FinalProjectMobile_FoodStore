package com.example.app_foodstore.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //Thay đổi ip
    private static final String BASE_URL = "http://196.169.1.12:8080/";

    public static final String IMAGE_BASE_URL = BASE_URL + "uploads/";
    private static Retrofit retrofit = null;

    public static APIService getService() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(APIService.class);
    }
}
