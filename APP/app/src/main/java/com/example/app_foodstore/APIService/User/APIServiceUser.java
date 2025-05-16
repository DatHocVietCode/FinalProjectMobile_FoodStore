package com.example.app_foodstore.APIService.User;

import com.example.app_foodstore.Model.request.UserUpdateRequest;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface APIServiceUser {

    @GET("/users/my-profile")
    Call<BaseResponse<UserRes>> getMyProfile(@Header("Authorization") String token);

    @Multipart
    @PUT("/users/upload")
    Call<BaseResponse<Void>> uploadImage(
            @Header("Authorization") String token,
            @Part MultipartBody.Part image,
            @Part("description") UserUpdateRequest request
    );
}
