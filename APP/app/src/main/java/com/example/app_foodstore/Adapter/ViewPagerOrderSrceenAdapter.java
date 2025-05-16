package com.example.app_foodstore.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_order_history;
import com.example.app_foodstore.Fragment.Fragment_order_ongoing;
import com.example.app_foodstore.Model.MyOrderPendingDTO;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerOrderSrceenAdapter extends FragmentStateAdapter {

    private List<MyOrderPendingDTO> ongoingOrders = new ArrayList<>();
    private List<MyOrderPendingDTO> historyOrders = new ArrayList<>();

    public ViewPagerOrderSrceenAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setData(List<MyOrderPendingDTO> ongoing, List<MyOrderPendingDTO> history) {
        this.ongoingOrders = ongoing != null ? ongoing : new ArrayList<>();
        this.historyOrders = history != null ? history : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Fragment_order_ongoing fragmentOngoing = new Fragment_order_ongoing();
                // Truyền dữ liệu qua bundle
                if (ongoingOrders != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_list", new ArrayList<>(ongoingOrders));
                    fragmentOngoing.setArguments(bundle);
                }
                return fragmentOngoing;
            case 1:
                Fragment_order_history fragmentHistory = new Fragment_order_history();
                if (historyOrders != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_list", new ArrayList<>(historyOrders));
                    fragmentHistory.setArguments(bundle);
                }
                return fragmentHistory;
            default:
                return new Fragment_order_ongoing();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
