package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.databinding.FragmentPaymentVnpayBinding;

public class Fragment_payment_vnPay extends Fragment {
    FragmentPaymentVnpayBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentVnpayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
