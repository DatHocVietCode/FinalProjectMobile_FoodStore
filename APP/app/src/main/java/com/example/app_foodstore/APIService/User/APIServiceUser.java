package com.example.app_foodstore.APIService.User;

import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIServiceUser {

    @GET("/users/my-profile")
    Call<BaseResponse<UserRes>> getMyProfile(@Header("Authorization") String token);
}
