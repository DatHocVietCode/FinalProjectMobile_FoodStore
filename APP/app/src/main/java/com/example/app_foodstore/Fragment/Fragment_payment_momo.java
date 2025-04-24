package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.databinding.FragmentPaymentMomoBinding;

public class Fragment_payment_momo extends Fragment {
    FragmentPaymentMomoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentMomoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
