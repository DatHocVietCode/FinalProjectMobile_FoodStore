package com.example.app_foodstore.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.VoucherAdapter;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.ViewModel.VoucherViewModel;
import com.example.app_foodstore.databinding.FragmentRcVoucherDisplayBinding;

import java.util.ArrayList;
import java.util.List;

public class Fragment_voucher_rc extends Fragment {
    private FragmentRcVoucherDisplayBinding binding;
    private VoucherAdapter adapter;
    private VoucherViewModel voucherViewModel;
    private int tabNum;

    public Fragment_voucher_rc() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getArgumentsFromParent();
        initView(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initObservers();
    }

    private void initViewModel() {
        voucherViewModel = new ViewModelProvider(requireActivity()).get(VoucherViewModel.class);
    }

    private void getArgumentsFromParent() {
        if (getArguments() != null) {
            tabNum = getArguments().getInt("tabNum", 0);
        }
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentRcVoucherDisplayBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentVoucherDisplayRc.setLayoutManager(layoutManager);
        adapter = new VoucherAdapter(getContext(), new ArrayList<>(), tabNum);
        binding.fragmentVoucherDisplayRc.setAdapter(adapter);
    }

    private void initObservers() {
        // Get token from SharedPreferences or any other appropriate method
        String token = getTokenFromPreferences();

        // Observe data from ViewModel based on tabNum
        LiveData<List<VoucherModel>> voucherLiveData;
        if (tabNum == 0) {
            voucherLiveData = voucherViewModel.getMyVouchers(token);
        } else {
            voucherLiveData = voucherViewModel.getAvailableVouchers(token);
        }

        voucherLiveData.observe(getViewLifecycleOwner(), vouchers -> {
            if (vouchers != null && !vouchers.isEmpty()) {
                adapter.updateVoucherList(vouchers);
                binding.fragmentVoucherDisplayRc.setVisibility(View.VISIBLE); // ✅ Hiện RecyclerView
                binding.fragmentVoucherDisplayTvNoVoucher.setVisibility(View.GONE); // Ẩn dòng "không có"
            } else {
                binding.fragmentVoucherDisplayRc.setVisibility(View.GONE); // Ẩn RecyclerView nếu không có dữ liệu
                binding.fragmentVoucherDisplayTvNoVoucher.setVisibility(View.VISIBLE); // Hiện thông báo
            }
        });
    }

    // Method to get token from SharedPreferences or any other storage method
    private String getTokenFromPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("access_token", ""); // hoặc null nếu bạn muốn kiểm tra
    }
}
