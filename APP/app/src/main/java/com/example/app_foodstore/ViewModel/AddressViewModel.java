package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.AddressModel;
import com.example.app_foodstore.Model.request.AddressRequest;
import com.example.app_foodstore.Model.response.AddressResponse;
import com.example.app_foodstore.Repo.AddressRepository;

import java.util.List;

public class AddressViewModel extends ViewModel {

    private final AddressRepository repository;

    public AddressViewModel() {
        repository = new AddressRepository();
    }

    public LiveData<List<AddressResponse>> getMyAddresses(String token) {
        return repository.getMyAddresses(token);
    }

    public LiveData<Boolean> addAddress(String token, AddressRequest request) {
        return repository.addAddress(token, request);
    }
    public LiveData<Boolean> deleteAddress(String token, Long addressId) {
        return repository.deleteAddress(token, addressId);
    }
    public LiveData<AddressResponse> getDefaultAddress(String token) {
        return repository.getDefaultAddress(token);
    }
}
