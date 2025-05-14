package com.example.app_foodstore.APIService.Voucher;

import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIServiceVoucher {
    @GET("/vouchers/my-vouchers")
    Call<BaseResponse<List<VoucherModel>>> getMyVoucher(@Header("Authorization") String token);
    @GET("vouchers/available")
    Call<BaseResponse<List<VoucherModel>>> getVoucherAvailable(@Header("Authorization") String token);
}
