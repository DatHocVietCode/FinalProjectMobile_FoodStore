package com.example.app_foodstore.APIService.Order;

import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIServiceOrder {
    @GET("/orders/my-orders/pending")
    Call<BaseResponse<List<MyOrderPendingDTO>>> getPendingOrders(@Header("Authorization") String token);

    @GET("/orders/my-orders/history")
    Call<BaseResponse<List<MyOrderPendingDTO>>> getCompleteOrders(@Header("Authorization") String token);
}
