package com.example.app_foodstore.APIService.Cart;

import com.example.app_foodstore.Model.CartItemModel;
import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.Model.request.UpdateCartRequest;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIServiceCart {
    @GET("/carts")
    Call<BaseResponse<List<CartModel>>> getMyCartItem(@Header("Authorization") String token);
    @PUT("/carts/update")
    Call<BaseResponse<Void>> updateCart(@Header("Authorization") String token, @Body UpdateCartRequest request);

    @POST("/carts/add")
    Call<BaseResponse<Void>> addCart(@Header("Authorization") String token, @Body UpdateCartRequest request);
    @DELETE("/carts/remove/{productId}")
    Call<BaseResponse<Void>> deleteCartItem(
            @Header("Authorization") String token,
            @Path("productId") Long productId
    );
}
