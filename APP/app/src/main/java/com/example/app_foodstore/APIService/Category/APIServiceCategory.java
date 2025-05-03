package com.example.app_foodstore.APIService.Category;

import com.example.app_foodstore.APIService.APIRespone;
import com.example.app_foodstore.Model.CategoryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceCategory {
    @GET("categories")
    Call<APIRespone<CategoryModel>> getCategories(
            @Query("category_id") Long categoryId
    );
}
