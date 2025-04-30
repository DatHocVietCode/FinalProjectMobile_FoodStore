package com.example.app_foodstore.APIService.Category;

import com.example.app_foodstore.APIService.APIRespone;
import com.example.app_foodstore.Model.CategoryModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServiceCategory {
    @GET("categories")
    Call<APIRespone<CategoryModel>> getCategories();
}
