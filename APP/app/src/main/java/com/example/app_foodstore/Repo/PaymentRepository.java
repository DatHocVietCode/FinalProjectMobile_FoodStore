package com.example.app_foodstore.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Food.APIServiceFood;
import com.example.app_foodstore.APIService.Payment.APIServicePayment;
import com.example.app_foodstore.Model.request.PaymentReOrderReq;
import com.example.app_foodstore.Model.request.PaymentRequest;
import com.example.app_foodstore.Model.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {
    private final APIServicePayment apiService;

    public PaymentRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIServicePayment.class);
    }

    public LiveData<Boolean> makePayment(String token, PaymentRequest paymentRequest) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.makePayment(bearerToken, paymentRequest).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                resultLiveData.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                resultLiveData.setValue(false);
            }
        });

        return resultLiveData;
    }
    public LiveData<Boolean> makeReOrder(String token, PaymentReOrderReq req) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.makeReOder(bearerToken, req).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                resultLiveData.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                resultLiveData.setValue(false);
            }
        });
        return resultLiveData;
    }
}
