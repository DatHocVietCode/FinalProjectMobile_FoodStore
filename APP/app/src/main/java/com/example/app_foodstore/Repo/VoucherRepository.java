package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Voucher.APIServiceVoucher;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherRepository {
    private final APIServiceVoucher apiService;

    public VoucherRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIServiceVoucher.class);
    }

    public LiveData<List<VoucherModel>> getMyVouchers(String token) {
        MutableLiveData<List<VoucherModel>> voucherData = new MutableLiveData<>();

        Call<BaseResponse<List<VoucherModel>>> call = apiService.getMyVoucher("Bearer " + token);
        call.enqueue(new Callback<BaseResponse<List<VoucherModel>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<VoucherModel>>> call, Response<BaseResponse<List<VoucherModel>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    voucherData.setValue(response.body().getData());
                } else {
                    Log.e("VoucherRepo", "Response error: "
                            + "isSuccessful=" + response.isSuccessful()
                            + ", code=" + response.code()
                            + ", message=" + response.message()
                            + ", body=" + response.body()
                    );
                    voucherData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VoucherModel>>> call, Throwable t) {
                Log.e("VoucherRepo", "API failed", t);
                voucherData.setValue(new ArrayList<>());
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
                    Log.e("VoucherRepo", "Response error: " + response.message());
                    voucherData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VoucherModel>>> call, Throwable t) {
                Log.e("VoucherRepo", "API failed", t);
                voucherData.setValue(new ArrayList<>());
            }
        });

        return voucherData;
    }

    public void toggleUserVoucher(Long voucherId, String token) {
        String bearerToken = "Bearer " + token;
        apiService.toggleUserVoucher(voucherId, bearerToken)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("VoucherRepo", "Voucher updated successfully");
                        } else {
                            Log.e("VoucherRepo", "API failed with code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("VoucherRepo", "Network error: " + t.getMessage());
                    }
                });
    }
}
