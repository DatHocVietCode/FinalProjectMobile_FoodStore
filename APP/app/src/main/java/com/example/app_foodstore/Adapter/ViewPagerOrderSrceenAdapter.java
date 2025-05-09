package com.example.app_foodstore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_order_history;
import com.example.app_foodstore.Fragment.Fragment_order_ongoing;

public class ViewPagerOrderSrceenAdapter extends FragmentStateAdapter {
    public ViewPagerOrderSrceenAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_order_ongoing();
            case 1:
                return new Fragment_order_history();
            default:
                return new Fragment_order_ongoing();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
