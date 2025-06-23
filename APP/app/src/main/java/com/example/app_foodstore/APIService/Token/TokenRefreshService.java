package com.example.app_foodstore.APIService.Token;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.Model.response.AccessTokenResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TokenRefreshService {
    @POST("/auth/refreshAccessToken")
    Call<APIResponse<AccessTokenResponse>> refreshToken(@Header("X-Refresh-Token") String refreshToken);
}
