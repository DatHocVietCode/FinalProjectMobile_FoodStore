package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.app_foodstore.Model.request.PaymentReOrderReq;
import com.example.app_foodstore.Model.request.PaymentRequest;
import com.example.app_foodstore.Repo.PaymentRepository;

public class PaymentViewModel extends ViewModel {
    private final PaymentRepository paymentRepository;

    public PaymentViewModel() {
        paymentRepository = new PaymentRepository();
    }

    public LiveData<Boolean> makePayment(String token, PaymentRequest paymentRequest) {
        return paymentRepository.makePayment(token, paymentRequest);
    }
    public LiveData<Boolean> reOrder(String token, PaymentReOrderReq req ){
        return paymentRepository.makeReOrder(token, req);
    }

}