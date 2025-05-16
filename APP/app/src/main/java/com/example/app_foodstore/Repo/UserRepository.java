package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceUser;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final APIServiceUser apiService;

    public UserRepository() {
        apiService = Constant.retrofit.create(APIServiceUser.class);
    }

    public LiveData<UserRes> getUserProfile(String token) {
        MutableLiveData<UserRes> userData = new MutableLiveData<>();

        apiService.getMyProfile("Bearer " + token).enqueue(new Callback<BaseResponse<UserRes>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserRes>> call, Response<BaseResponse<UserRes>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userData.postValue(response.body().getData());
                } else {
                    Log.e("UserRepository", "Failed: " + response.message());
                    userData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserRes>> call, Throwable t) {
                Log.e("UserRepository", "API call failed: " + t.getMessage());
                userData.postValue(null);
            }
        });

        return userData;
    }
}
