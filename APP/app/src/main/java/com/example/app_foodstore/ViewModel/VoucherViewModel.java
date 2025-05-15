package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.Repo.VoucherRepository;

import java.util.List;

public class VoucherViewModel extends ViewModel {
    private final VoucherRepository repository = new VoucherRepository();

    public LiveData<List<VoucherModel>> getMyVouchers(String token) {
        return repository.getMyVouchers(token);
    }

    public LiveData<List<VoucherModel>> getAvailableVouchers(String token) {
        return repository.getAvailableVouchers(token);
    }

    public void toggleUserVoucher(Long voucherId, String token) {
       repository.toggleUserVoucher(voucherId, token);
    }

}