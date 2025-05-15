package com.example.app_foodstore.APIService.User;

import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServiceAuth {
    @POST("/auth/login")
    Call<BaseResponse<UserLoginRes>> login(@Body UserLoginReq userLoginReq);


}
