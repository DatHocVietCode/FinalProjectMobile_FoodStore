package com.example.app_foodstore.APIService.User;

import com.example.app_foodstore.Model.request.OTPRequestDTO;
import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.request.UserSignUpRequest;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;
import com.example.app_foodstore.Model.response.UserSignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServiceAuth {
    @POST("/auth/login")
    Call<BaseResponse<UserLoginRes>> login(@Body UserLoginReq userLoginReq);

    @POST("/auth/signUp")
    Call<BaseResponse<UserSignUpResponse>> signUp(@Body UserSignUpRequest userSignUpRequest);

    @POST("/auth/resendOTP")
    Call<BaseResponse<String>> resendOTP();

    @POST("/auth/verifyOTP")
    Call<BaseResponse<String>> verifyOTP(@Body OTPRequestDTO otpRequestDTO);
}
