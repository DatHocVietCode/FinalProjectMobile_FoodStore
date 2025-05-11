package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.OrderHistoryAdapter;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.ViewModel.OrderViewModel;
import com.example.app_foodstore.databinding.FragmentOrderHistoryBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_order_history extends Fragment {
    OrderViewModel orderViewModel;
    FragmentOrderHistoryBinding binding;
    public Fragment_order_history() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentOrderHistoryRc.setLayoutManager(layoutManager);

        OrderHistoryAdapter adapter = new OrderHistoryAdapter(getContext(), orderViewModel);

        binding.fragmentOrderHistoryRc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        orderViewModel.getHistoryOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter.notifyDataSetChanged();
            Log.d("OrderHistory", "OrderHistory observe được dữ liệu mới ");
        });
        return binding.getRoot();
    }
}
