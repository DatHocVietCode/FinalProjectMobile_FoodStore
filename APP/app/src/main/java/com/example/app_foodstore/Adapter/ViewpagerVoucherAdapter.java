package com.example.app_foodstore.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_voucher_rc;

public class ViewpagerVoucherAdapter extends FragmentStateAdapter {
    private int tabNum;  // Thêm biến tabNum

    // Cập nhật constructor để nhận thêm tham số int tabNum
    public ViewpagerVoucherAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment_voucher_rc fragment = new Fragment_voucher_rc();
        Bundle args = new Bundle();
        args.putInt("tabNum", position); // <-- CHỈNH LẠI: dùng position làm tabNum
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;  // Số lượng tab
    }
}

