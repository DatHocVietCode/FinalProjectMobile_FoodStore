package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.APIService.Order.APIServiceOrder;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private final APIServiceOrder apiServiceOrder;

    public OrderRepository() {
        this.apiServiceOrder = RetrofitClient.getRetrofitInstance().create(APIServiceOrder.class);
    }

    public void fetchPendingOrders(MutableLiveData<List<MyOrderPendingDTO>> liveData) {
        apiServiceOrder.getPendingOrders().enqueue(new Callback<BaseResponse<List<MyOrderPendingDTO>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MyOrderPendingDTO>>> call, Response<BaseResponse<List<MyOrderPendingDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MyOrderPendingDTO> orders = response.body().getData();
                    liveData.postValue(orders);

                    if (orders != null) {
                        for (MyOrderPendingDTO order : orders) {
                            Log.d("DEBUG_ORDER", new Gson().toJson(order));
                        }
                    }


                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<MyOrderPendingDTO>>> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void fetchCompleteOrders(MutableLiveData<List<MyOrderPendingDTO>> liveData) {
        apiServiceOrder.getCompleteOrders().enqueue(new Callback<BaseResponse<List<MyOrderPendingDTO>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MyOrderPendingDTO>>> call, Response<BaseResponse<List<MyOrderPendingDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body().getData());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<MyOrderPendingDTO>>> call, Throwable t) {
                Log.e("DEBUG_ORDER_FAILURE", "API call failed", t);
                liveData.postValue(null);
            }
        });
    }

    public void cancelOrder(String token, Long orderId, CancelCallback callback) {
        apiServiceOrder.cancelOrder(token, orderId).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful()) {
                    callback.onCancelSuccess();
                } else {
                    callback.onCancelFailed("Cancel failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                callback.onCancelFailed(t.getMessage());
            }
        });
    }

    // Optional interface for cancel callback
    public interface CancelCallback {
        void onCancelSuccess();
        void onCancelFailed(String errorMessage);
    }
}
