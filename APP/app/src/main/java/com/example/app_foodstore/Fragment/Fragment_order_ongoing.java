package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.OrderOnGoingAdapter;
import com.example.app_foodstore.databinding.FragmentOrderOngoingBinding;

public class Fragment_order_ongoing extends Fragment {
    FragmentOrderOngoingBinding binding;
    public Fragment_order_ongoing() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderOngoingBinding.inflate(inflater, container, false);
        // Sau này chỉnh nữa
        binding.fragmentOrderOngoingRc.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.fragmentOrderOngoingRc.setAdapter(new OrderOnGoingAdapter(getContext()));
        return binding.getRoot();
    }
}
