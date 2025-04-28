package com.example.app_foodstore.API;

import com.example.app_foodstore.CategoryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIService {
    @GET("categories")
    Call<CategoryResponse> getAllCategories();
}
