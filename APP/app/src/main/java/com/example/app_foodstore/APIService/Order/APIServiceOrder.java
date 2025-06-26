package com.example.app_foodstore.APIService.Order;

import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIServiceOrder {
    @GET("orders/my-orders/pending")
    Call<BaseResponse<List<MyOrderPendingDTO>>> getPendingOrders();

    @GET("orders/my-orders/history")
    Call<BaseResponse<List<MyOrderPendingDTO>>> getCompleteOrders();
    // PUT - Cancel order
    @PUT("orders/cancel/{orderId}")
    Call<BaseResponse<Void>> cancelOrder(@Header("Authorization") String token, @Path("orderId") Long orderId);

    @GET("orders/track/{orderId}")
    Call<BaseResponse<Void>> trackOrder(@Header("Authorization") String token, @Path("orderId") Long orderId);
}
