package com.example.app_foodstore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_foodDisplay;
import com.example.app_foodstore.Fragment.Fragment_order_history;
import com.example.app_foodstore.Fragment.Fragment_order_ongoing;

public class ViewPagerFoodTabLayoutAdapter extends FragmentStateAdapter {

    public ViewPagerFoodTabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_foodDisplay(0);
            case 1:
                return new Fragment_foodDisplay(1);
            case 2:
                return new Fragment_foodDisplay(2);
            default:
                return new Fragment_foodDisplay(0);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
