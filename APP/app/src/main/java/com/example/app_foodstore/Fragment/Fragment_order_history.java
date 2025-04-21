package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.OrderHistoryAdapter;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.databinding.FragmentOrderHistoryBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_order_history extends Fragment {
    public Fragment_order_history() {}
    FragmentOrderHistoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentOrderHistoryRc.setLayoutManager(layoutManager);
        List<OrderModel> listOrder = new ArrayList<>();
        listOrder.add(new OrderModel(true, new Date()));
        listOrder.add(new OrderModel(true, new Date()));
        listOrder.add(new OrderModel(false, new Date()));
        listOrder.add(new OrderModel(true, new Date()));
        listOrder.add(new OrderModel(false, new Date()));
        OrderHistoryAdapter adapter = new OrderHistoryAdapter(getContext(), listOrder);
        binding.fragmentOrderHistoryRc.setAdapter(adapter);
        return binding.getRoot();
    }
}
