package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Address.APIServiceAddress;
import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.Model.AddressModel;
import com.example.app_foodstore.Model.request.AddressRequest;
import com.example.app_foodstore.Model.response.AddressResponse;
import com.example.app_foodstore.Model.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {

    private final APIServiceAddress apiService;

    public AddressRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIServiceAddress.class);
    }

    public LiveData<List<AddressResponse>> getMyAddresses(String token) {
        MutableLiveData<List<AddressResponse>> addressData = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.getListMyAddress(bearerToken).enqueue(new Callback<BaseResponse<List<AddressResponse>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<AddressResponse>>> call, Response<BaseResponse<List<AddressResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressData.setValue(response.body().getData());
                } else {
                    Log.e("AddressRepo", "Failed to load addresses: " + response.code());
                    addressData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<AddressResponse>>> call, Throwable t) {
                Log.e("AddressRepo", "Error fetching addresses", t);
                addressData.setValue(new ArrayList<>());
            }
        });

        return addressData;
    }

    public LiveData<Boolean> addAddress(String token, AddressRequest request) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.addAddress(bearerToken, request).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                result.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                Log.e("AddressRepo", "Error adding address", t);
                result.setValue(false);
            }
        });

        return result;
    }
    public LiveData<Boolean> deleteAddress(String token, Long addressId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        String bearerToken = "Bearer " + token;

        apiService.deleteAddress(bearerToken, addressId).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                result.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                Log.e("AddressRepo", "Error deleting address", t);
                result.setValue(false);
            }
        });

        return result;
    }

    public LiveData<AddressResponse> getDefaultAddress(String token) {
        MutableLiveData<AddressResponse> data = new MutableLiveData<>();

        apiService.getDefaultAddress("Bearer " + token).enqueue(new Callback<BaseResponse<AddressResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AddressResponse>> call, Response<BaseResponse<AddressResponse>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AddressResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
