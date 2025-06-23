package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Comment.APIServiceComment;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.Model.request.CommentRequestDTO;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRepository {

    private final APIServiceComment apiService;

    public CommentRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIServiceComment.class);
    }

    public void postComment(String token, CommentRequestDTO dto, MutableLiveData<BaseResponse<Void>> liveData) {
        apiService.postComment("Bearer " + token, dto).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    Log.d("Add Comment","Khong Thanh Cong");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                Log.d("Repo", "onResponse: error");
            }
        });
    }
}
