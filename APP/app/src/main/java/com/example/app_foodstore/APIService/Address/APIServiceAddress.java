package com.example.app_foodstore.APIService.Address;

import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.Model.request.AddressRequest;
import com.example.app_foodstore.Model.request.UpdateCartRequest;
import com.example.app_foodstore.Model.response.AddressResponse;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServiceAddress {
    @GET("/address/my-address")
    Call<BaseResponse<List<AddressResponse>>> getListMyAddress(@Header("Authorization") String token);
    @POST("/address/add")
    Call<BaseResponse<Void>> addAddress(@Header("Authorization") String token, @Body AddressRequest request);
    @DELETE("/address/delete/{idAddress}")
    Call<BaseResponse<Void>> deleteAddress(@Header("Authorization") String token, @Path("idAddress") Long addressId);
    @GET("/address/default")
    Call<BaseResponse<AddressResponse>> getDefaultAddress(@Header("Authorization") String token);
}
