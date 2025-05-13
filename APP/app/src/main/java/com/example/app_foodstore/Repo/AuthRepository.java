package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceAuth;
import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserLoginRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final APIServiceAuth authService;

    public AuthRepository() {
        authService = Constant.retrofit.create(APIServiceAuth.class);
    }

    public LiveData<UserLoginRes> login(UserLoginReq req) {
        MutableLiveData<UserLoginRes> loginData = new MutableLiveData<>();

        Call<BaseResponse<UserLoginRes>> call = authService.login(req);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<UserLoginRes>> call, Response<BaseResponse<UserLoginRes>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        loginData.setValue(response.body().getData());
                        Log.d("AuthRepo", "Login success: " + response.body().getData().getAccessToken());
                    } else {
                        Log.d("AuthRepo", "Login failed with status != 200");
                        loginData.setValue(null);
                    }
                } else {
                    Log.d("AuthRepo", "Response unsuccessful or body null");
                    loginData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserLoginRes>> call, Throwable t) {
                Log.e("AuthRepo", "Login failed: " + t.getMessage());
                loginData.setValue(null);
            }
        });

        return loginData;
    }
}
