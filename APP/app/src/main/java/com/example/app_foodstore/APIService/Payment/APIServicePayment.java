package com.example.app_foodstore.APIService.Payment;

import com.example.app_foodstore.Model.request.PaymentReOrderReq;
import com.example.app_foodstore.Model.request.PaymentRequest;
import com.example.app_foodstore.Model.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServicePayment {

    @POST("/payment")
    Call<BaseResponse<Void>> makePayment(@Header("Authorization") String token, @Body PaymentRequest paymentRequest);
    @POST("/payment/re-order")
    Call<BaseResponse<Void>> makeReOder(@Header("Authorization") String token, @Body PaymentReOrderReq req);
}
