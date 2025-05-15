package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Cart.APIServiceCart;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.Model.request.UpdateCartRequest;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    private final APIServiceCart apiService;

    public CartRepository() {
        apiService = Constant.retrofit.create(APIServiceCart.class);
    }

    public LiveData<List<CartModel>> getMyCart(String token) {
        MutableLiveData<List<CartModel>> cartData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.getMyCartItem(bearerToken).enqueue(new Callback<BaseResponse<List<CartModel>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<CartModel>>> call, Response<BaseResponse<List<CartModel>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cartData.setValue(response.body().getData());
                } else {
                    Log.e("CartRepo", "Response failed: " + response.code());
                    cartData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<CartModel>>> call, Throwable t) {
                Log.e("CartRepo", "API error", t);
                cartData.setValue(new ArrayList<>());
            }
        });

        return cartData;
    }
    public LiveData<Boolean> updateCartItem(String token, Long productId, Integer quantity) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        UpdateCartRequest request = new UpdateCartRequest(productId, quantity);
        apiService.updateCart(bearerToken, request).enqueue(new Callback<BaseResponse<Void>>() {
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

    public LiveData<Boolean> deleteCartItem(String token, Long productId) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.deleteCartItem(bearerToken, productId).enqueue(new Callback<BaseResponse<Void>>() {
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
    public LiveData<Boolean> addCartItem(String token, Long productId, int quantity) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        UpdateCartRequest request = new UpdateCartRequest(productId, quantity);

        Log.d("DEBUG", "Bearer Token: " + bearerToken);
        Log.d("DEBUG", "Product ID: " + productId);
        Log.d("DEBUG", "Quantity: " + quantity);

        apiService.addCart(bearerToken, request).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG", "Add to cart success!");
                    resultLiveData.setValue(true);
                } else {
                    Log.e("ERROR", "Response failed with status code: " + response.code());

                    // Nếu server có trả về lỗi chi tiết:
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("ERROR", "Error body: " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    resultLiveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                Log.e("ERROR", "API call failed: " + t.getMessage());
                resultLiveData.setValue(false);
            }
        });

        return resultLiveData;
    }



}
