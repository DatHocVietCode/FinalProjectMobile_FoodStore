package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.APIRespone;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Food.APIServiceFood;
import com.example.app_foodstore.Model.FoodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {
    private APIServiceFood apiService;

    public FoodRepository() {
        apiService = Constant.retrofit.create(APIServiceFood.class);
    }

    public LiveData<List<FoodModel>> getNewFood() {
        MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
        Call<APIRespone<FoodModel>> call = apiService.getNewProducts("", 1L, "", "");
        call.enqueue(new Callback<APIRespone<FoodModel>>() {
            @Override
            public void onResponse(Call<APIRespone<FoodModel>> call, Response<APIRespone<FoodModel>> response) {
                if (response.body() != null) {
                    foodData.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                }
            }

            @Override
            public void onFailure(Call<APIRespone<FoodModel>> call, Throwable t) {
                Log.e("errorr", "onFailure: " + t.getMessage());
            }
        });
        return foodData;
    }
}

