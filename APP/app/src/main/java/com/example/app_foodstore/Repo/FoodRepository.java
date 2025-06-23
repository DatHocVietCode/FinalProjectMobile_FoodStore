package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.APIService.APIResponePagination;
import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Food.APIServiceFood;
import com.example.app_foodstore.Model.Data;
import com.example.app_foodstore.Model.FoodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {
    private APIServiceFood apiService;

    public FoodRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIServiceFood.class);
    }
    private String TurnEmptyToNull(String keyword)
    {
        if (keyword == null)
            return keyword;
        if (keyword.isEmpty())
        {
            keyword = null;
        }
        return keyword;
    }
    public LiveData<List<FoodModel>> getNewFood(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
        sortByName = TurnEmptyToNull(sortByName);
        sortByPrice = TurnEmptyToNull(sortByPrice);
        Call<APIResponse<FoodModel>> call = apiService.getNewProducts(keyword, categoryId, sortByName, sortByPrice);
        call.enqueue(new Callback<APIResponse<FoodModel>>() {
            @Override
            public void onResponse(Call<APIResponse<FoodModel>> call, Response<APIResponse<FoodModel>> response) {
                if (response.body() != null) {
                    foodData.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                }
            }

            @Override
            public void onFailure(Call<APIResponse<FoodModel>> call, Throwable t) {
                Log.e("errorr", "onFailure: " + t.getMessage());
            }
        });
        return foodData;
    }
    public LiveData<List<FoodModel>> getBestSeller(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
        sortByName = TurnEmptyToNull(sortByName);
        sortByPrice = TurnEmptyToNull(sortByPrice);
        Call<APIResponse<FoodModel>> call = apiService.getBestSellerProducts(keyword, categoryId, sortByName, sortByPrice);
        call.enqueue(new Callback<APIResponse<FoodModel>>() {
            @Override
            public void onResponse(Call<APIResponse<FoodModel>> call, Response<APIResponse<FoodModel>> response) {
                if (response.body() != null) {
                    foodData.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                    List<FoodModel> foods = response.body().getData();

                }
            }

            @Override
            public void onFailure(Call<APIResponse<FoodModel>> call, Throwable t) {
                Log.e("errorr", "onFailure: " + t.getMessage());
            }
        });
        return foodData;
    }
    public LiveData<List<FoodModel>> getFoods(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        MutableLiveData<Data> data = new MutableLiveData<>();
        MutableLiveData<List<FoodModel>> foodData = new MutableLiveData<>();
        sortByName = TurnEmptyToNull(sortByName);
        sortByPrice = TurnEmptyToNull(sortByPrice);
        Log.d("API Request", "keyword: " + keyword + ", categoryId: " + categoryId + ", sortByName: " + sortByName + ", sortByPrice: " + sortByPrice);
        Call<APIResponePagination> call = apiService.getProducts(keyword, categoryId, sortByName, sortByPrice);
        call.enqueue(new Callback<APIResponePagination>() {
           @Override
           public void onResponse(Call<APIResponePagination> call, Response<APIResponePagination> response) {
               if (response.body() != null) {
                   //Log.d("API Response", response.body().getData().toString());
                   data.setValue(response.body().getData()); // Lưu dữ liệu vào LiveData
                   foodData.setValue(data.getValue().getFoods()); // Lưu dữ liệu vào LiveData
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

