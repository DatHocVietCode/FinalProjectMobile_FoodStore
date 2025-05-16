package com.example.app_foodstore.APIService.Comment;

import com.example.app_foodstore.Model.request.CommentRequestDTO;
import com.example.app_foodstore.Model.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServiceComment {
    @POST("/comments/add")
    Call<BaseResponse<Void>> postComment(@Header("Authorization") String token, @Body CommentRequestDTO commentRequest);
}
