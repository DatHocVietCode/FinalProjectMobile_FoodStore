package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.OrderOnGoingAdapter;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.ViewModel.OrderViewModel;
import com.example.app_foodstore.databinding.FragmentOrderOngoingBinding;

import java.util.ArrayList;
import java.util.List;

public class Fragment_order_ongoing extends Fragment {
    OrderViewModel orderViewModel;
    FragmentOrderOngoingBinding binding;
    public Fragment_order_ongoing() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderOngoingBinding.inflate(inflater, container, false);
        // Sau này chỉnh nữa
        binding.fragmentOrderOngoingRc.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderOnGoingAdapter adapter = new OrderOnGoingAdapter(getContext(), orderViewModel.getOngoingOrders().getValue());
        binding.fragmentOrderOngoingRc.setAdapter(adapter);
        orderViewModel.getOngoingOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter.setListOrderOngoing(orders);
            adapter.notifyDataSetChanged();
        });
        return binding.getRoot();
    }
}
