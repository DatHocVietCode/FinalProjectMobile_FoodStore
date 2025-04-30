package com.example.app_foodstore.Repo;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.APIRespone;
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
        Call<APIRespone<CategoryModel>> call = apiService.getCategories();
        call.enqueue(new Callback<APIRespone<CategoryModel>>() {
            @Override
            public void onResponse(Call<APIRespone<CategoryModel>> call, Response<APIRespone<CategoryModel>> response) {
                if (response.body() != null) {
                    categoryData.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                    Log.d("Repo", "onResponse: " + response.body().getData());
                }
                else {
                    Log.d("Repo", "onResponse: null");
                }
            }
            @Override
            public void onFailure(Call<APIRespone<CategoryModel>> call, Throwable t) {
                Log.d("Repo", "onResponse: error");
            }
        });
        return categoryData;
    }
}
