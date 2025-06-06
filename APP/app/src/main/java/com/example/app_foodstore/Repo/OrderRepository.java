package com.example.app_foodstore.Repo;

import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.APIService.Order.APIServiceOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private final APIServiceOrder apiServiceOrder;

    public OrderRepository(APIServiceOrder apiServiceOrder) {
        this.apiServiceOrder = apiServiceOrder;
    }

    public void fetchPendingOrders(String token, MutableLiveData<List<MyOrderPendingDTO>> liveData) {
        apiServiceOrder.getPendingOrders(token).enqueue(new Callback<BaseResponse<List<MyOrderPendingDTO>>>() {
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
                liveData.postValue(null);
            }
        });
    }

    public void fetchCompleteOrders(String token, MutableLiveData<List<MyOrderPendingDTO>> liveData) {
        apiServiceOrder.getCompleteOrders(token).enqueue(new Callback<BaseResponse<List<MyOrderPendingDTO>>>() {
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
