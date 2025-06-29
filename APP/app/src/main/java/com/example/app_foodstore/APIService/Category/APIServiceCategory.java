package com.example.app_foodstore.APIService.Category;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.Model.CategoryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceCategory {
    @GET("categories")
    Call<APIResponse<CategoryModel>> getCategories(
            @Query("category_id") Long categoryId
    );
}
