package com.example.app_foodstore.APIService.Food;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.APIService.APIResponePagination;
import com.example.app_foodstore.Model.FoodModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceFood {
    @GET("products/new")
    Call<APIResponse<FoodModel>> getNewProducts(
            @Query("keyword") String keyword,
            @Query("category_id") Long categoryId,
            @Query("sort_by_name") String sortByName,
            @Query("sort_by_price") String sortByPrice
    );
    @GET("products/best-seller")
    Call<APIResponse<FoodModel>> getBestSellerProducts(
            @Query("keyword") String keyword,
            @Query("category_id") Long categoryId,
            @Query("sort_by_name") String sortByName,
            @Query("sort_by_price") String sortByPrice
    );
    @GET("products")
    Call<APIResponePagination> getProducts(
            @Query("keyword") String keyword,
            @Query("category_id") Long categoryId,
            @Query("sort_by_name") String sortByName,
            @Query("sort_by_price") String sortByPrice
    );
}
