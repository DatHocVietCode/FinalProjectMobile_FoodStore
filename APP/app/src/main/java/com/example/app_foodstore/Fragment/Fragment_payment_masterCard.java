package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.databinding.FragmentPaymentMastercardBinding;

public class Fragment_payment_masterCard extends Fragment {
    FragmentPaymentMastercardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentMastercardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
