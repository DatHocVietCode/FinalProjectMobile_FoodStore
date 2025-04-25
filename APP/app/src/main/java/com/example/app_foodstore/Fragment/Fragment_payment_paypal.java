package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.databinding.FragmentPaymentPaypalBinding;

public class Fragment_payment_paypal extends Fragment {
    FragmentPaymentPaypalBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentPaypalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }
}
