package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.APIService.Category.APIServiceCategory;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.Model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepo {
    private APIServiceCategory apiService;
    public CategoryRepo() {
        apiService = Constant.retrofit.create(APIServiceCategory.class);
    }
    public LiveData<List<CategoryModel>> getCategories() {
        MutableLiveData<List<CategoryModel>> categoryData = new MutableLiveData<>();
        Call<APIResponse<CategoryModel>> call = apiService.getCategories(null);
        call.enqueue(new Callback<APIResponse<CategoryModel>>() {
            @Override
            public void onResponse(Call<APIResponse<CategoryModel>> call, Response<APIResponse<CategoryModel>> response) {
                if (response.body() != null) {
                    categoryData.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                    Log.d("Repo", "onResponse: " + response.body().getData());
                }
                else {
                    Log.d("Repo", "onResponse: null");
                }
            }
            @Override
            public void onFailure(Call<APIResponse<CategoryModel>> call, Throwable t) {
                Log.d("Repo", "onResponse: error");
            }
        });
        return categoryData;
    }
    public LiveData<List<CategoryModel>> getCategoriesById(Long id) {
        MutableLiveData<List<CategoryModel>> categoryData = new MutableLiveData<>();
        Call<APIResponse<CategoryModel>> call = apiService.getCategories(id);
        call.enqueue(new Callback<APIResponse<CategoryModel>>() {
            @Override
            public void onResponse(Call<APIResponse<CategoryModel>> call, Response<APIResponse<CategoryModel>> response) {
                if (response.body() != null) {
                    categoryData.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                    Log.d("Repo", "onResponse: " + response.body().getData());
                }
                else {
                    Log.d("Repo", "onResponse: null");
                }
            }
            @Override
            public void onFailure(Call<APIResponse<CategoryModel>> call, Throwable t) {
                Log.d("Repo", "onResponse: error");
            }
        });
        return categoryData;
    }

}
