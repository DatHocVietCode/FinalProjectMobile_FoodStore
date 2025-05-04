package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.VoucherAdapter;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.databinding.FragmentRcVoucherDisplayBinding;

import java.util.ArrayList;
import java.util.List;

public class Fragment_voucher_rc extends Fragment {
    FragmentRcVoucherDisplayBinding binding;
    VoucherAdapter adapter;
    // VoucherViewModel voucherModel; Chờ API
    int tabNum;
    public Fragment_voucher_rc() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getArgumentsFromParent();
        initView(inflater, container);
        return binding.getRoot();
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
        List<VoucherModel> voucherList = new ArrayList<>();
        voucherList.add(new VoucherModel("Giảm giá Tết Kìn chá nà Tung Tung Tung Sahur", 50, 200000));
        voucherList.add(new VoucherModel("Mừng Khai Trương", 30, 100000));
        voucherList.add(new VoucherModel("Voucher Đặc Biệt", 70, 500000));
        voucherList.add(new VoucherModel("Giảm Cuối Tuần", 20, 50000));
        voucherList.add(new VoucherModel("Flash Sale", 40, 150000));
        voucherList.add(new VoucherModel("Giảm giá Tết", 50, 200000));
        voucherList.add(new VoucherModel("Mừng Khai Trương", 30, 100000));
        voucherList.add(new VoucherModel("Voucher Đặc Biệt", 70, 500000));
        voucherList.add(new VoucherModel("Giảm Cuối Tuần", 20, 50000));
        voucherList.add(new VoucherModel("Flash Sale", 40, 150000));
        adapter = new VoucherAdapter(getContext(), voucherList, tabNum);
        binding.fragmentVoucherDisplayRc.setAdapter(adapter);
        Log.d("tabnum", "Tabnum: " +tabNum);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initViewModel();
        //initObservers();
    }

}
