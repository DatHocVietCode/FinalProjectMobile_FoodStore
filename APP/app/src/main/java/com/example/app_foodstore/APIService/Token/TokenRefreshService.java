package com.example.app_foodstore.APIService.Token;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.Model.response.AccessTokenResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TokenRefreshService {
    @POST("/auth/refresh")
    Call<APIResponse<AccessTokenResponse>> refreshToken(@Query("refreshToken") String refreshToken);
}
