package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.APIRespone;
import com.example.app_foodstore.APIService.APIResponePagination;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Food.APIServiceFood;
import com.example.app_foodstore.Model.Data;
import com.example.app_foodstore.Model.FoodModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {
    private APIServiceFood apiService;

    public FoodRepository() {
        apiService = Constant.retrofit.create(APIServiceFood.class);
    }

    public LiveData<List<FoodModel>> getNewFood(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
        Call<APIRespone<FoodModel>> call = apiService.getNewProducts(keyword, categoryId, sortByName, sortByPrice);
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
    public LiveData<List<FoodModel>> getBestSeller(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
        Call<APIRespone<FoodModel>> call = apiService.getBestSellerProducts(keyword, categoryId, sortByName, sortByPrice);
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
    public LiveData<List<FoodModel>> getFoods(String keyword, Long categoryId, String sortByName, String sortByPrice) {
       MutableLiveData<Data> data = new MutableLiveData<>();
       MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
       Call<APIResponePagination> call = apiService.getProducts(keyword, categoryId, sortByName, sortByPrice);
       call.enqueue(new Callback<APIResponePagination>() {
           @Override
           public void onResponse(Call<APIResponePagination> call, Response<APIResponePagination> response) {
               if (response.body() != null) {
                   Log.d("API Response", response.body().getData().toString());
                   data.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                   foodData.setValue(data.getValue().getFoods()); // Lưu dữ liệu vào LiveData

                  /* Log.d("Success", "Page Number: " + Objects.requireNonNull(data.getValue()).getPageNumber());
                   Log.d("Success", "Total Element: " + Objects.requireNonNull(data.getValue()).getTotalElements());
                   Log.d("Success", "Total Pages: " + Objects.requireNonNull(data.getValue()).getTotalPages());
                   Log.d("Success", "PageSize: " + Objects.requireNonNull(data.getValue()).getPageSize());
                   Log.d("Success", "onResponse: " + Objects.requireNonNull(data.getValue()).getFoods().get(0).getName());*/
               }
           }
           @Override
           public void onFailure(Call<APIResponePagination> call, Throwable t) {
               Log.d("Failure", "onFailure: " + t.getMessage());
           }
       });
       return foodData;
    }

}

