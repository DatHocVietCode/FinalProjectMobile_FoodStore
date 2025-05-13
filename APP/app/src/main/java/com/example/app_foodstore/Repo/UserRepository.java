package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceAuth;
import com.example.app_foodstore.APIService.User.APIServiceUser;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final APIServiceUser apiService;

    public UserRepository() {
        // Khởi tạo APIServiceUser
        apiService = Constant.retrofit.create(APIServiceUser.class);
    }

    public LiveData<UserRes> getUserProfile(String token) {
        final MutableLiveData<UserRes> userData = new MutableLiveData<>();

        // Gọi API getMyProfile
        apiService.getMyProfile("Bearer " + token).enqueue(new Callback<BaseResponse<UserRes>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserRes>> call, Response<BaseResponse<UserRes>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu API trả về thành công, cập nhật dữ liệu vào LiveData
                    userData.setValue(response.body().getData());
                } else {
                    // Nếu API không thành công, log lỗi và trả về null
                    Log.e("UserRepository", "Error: " + response.message());
                    userData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserRes>> call, Throwable t) {
                // Log lỗi nếu không thể kết nối
                Log.e("UserRepository", "API call failed: " + t.getMessage());
                userData.setValue(null);
            }
        });

        return userData;
    }
}