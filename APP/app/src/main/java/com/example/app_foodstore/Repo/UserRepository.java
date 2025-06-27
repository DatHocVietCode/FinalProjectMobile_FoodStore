package com.example.app_foodstore.Repo;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceUser;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;
import com.example.app_foodstore.Utils.ImageUtils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class UserRepository {

    private final APIServiceUser apiService;

    public UserRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIServiceUser.class);
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
    public void updateUserProfile(String fullname, String email, String phone, MultipartBody.Part avatarUri) {
        // Thực hiện gọi API để cập nhật thông tin người dùng ở đây
        Call<BaseResponse<Void>> call = apiService.updateUser(
                RequestBody.create(MediaType.parse("text/plain"), fullname),
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), phone),
                avatarUri);
        call.enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful()) {
                    Log.d("UserRepo", "Update user profile successful");
                } else {
                    Log.e("UserRepo", "Error when updating user profile: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                Log.e("UserRepo", "UserRepoFailure when updating user profile: ", t);
            }
        });
    }
}
