package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Voucher.APIServiceVoucher;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherRepository {
    private APIServiceVoucher apiService;

    public VoucherRepository() {
        apiService = Constant.retrofit.create(APIServiceVoucher.class);
    }
    public LiveData<List<VoucherModel>> getMyVouchers(String token) {
        MutableLiveData<List<VoucherModel>> voucherData = new MutableLiveData<>();

        // Gọi đúng API và đúng kiểu dữ liệu trả về
        Call<BaseResponse<List<VoucherModel>>> call = apiService.getMyVoucher("Bearer " + token);

        call.enqueue(new Callback<BaseResponse<List<VoucherModel>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<VoucherModel>>> call, Response<BaseResponse<List<VoucherModel>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    voucherData.setValue(response.body().getData());
                } else {
                    Log.e("VoucherRepository", "getMyVouchers API error: " + (response.message() != null ? response.message() : "Unknown error"));
                    voucherData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VoucherModel>>> call, Throwable t) {
                Log.e("VoucherRepository", "getMyVouchers failed: " + t.getMessage(), t);
                voucherData.setValue(null);
            }
        });

        return voucherData;
    }

    public LiveData<List<VoucherModel>> getAvailableVouchers(String token) {
        MutableLiveData<List<VoucherModel>> voucherData = new MutableLiveData<>();

        Call<BaseResponse<List<VoucherModel>>> call = apiService.getVoucherAvailable("Bearer " + token);

        call.enqueue(new Callback<BaseResponse<List<VoucherModel>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<VoucherModel>>> call, Response<BaseResponse<List<VoucherModel>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    voucherData.setValue(response.body().getData());
                } else {
                    Log.e("VoucherRepository", "getAvailableVouchers API error: " + (response.message() != null ? response.message() : "Unknown error"));
                    voucherData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VoucherModel>>> call, Throwable t) {
                Log.e("VoucherRepository", "getAvailableVouchers failed: " + t.getMessage(), t);
                voucherData.setValue(null);
            }
        });

        return voucherData;
    }
}
