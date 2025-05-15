package com.example.app_foodstore.Adapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.collection.SparseArrayCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_voucher_rc;

public class ViewpagerVoucherAdapter extends FragmentStateAdapter {

    private final SparseArrayCompat<Fragment> registeredFragments = new SparseArrayCompat<>();

    public ViewpagerVoucherAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment_voucher_rc fragment = new Fragment_voucher_rc();
        Bundle bundle = new Bundle();
        bundle.putInt("tabNum", position);
        fragment.setArguments(bundle);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Phương thức để lấy fragment hiện tại theo vị trí tab
    public Fragment_voucher_rc getFragment(int position) {
        return (Fragment_voucher_rc) registeredFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return 2; // hoặc số tab của bạn
    }
}


