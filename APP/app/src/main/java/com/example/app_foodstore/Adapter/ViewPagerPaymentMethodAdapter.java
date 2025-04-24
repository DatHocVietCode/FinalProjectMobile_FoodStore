package com.example.app_foodstore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_order_history;
import com.example.app_foodstore.Fragment.Fragment_order_ongoing;
import com.example.app_foodstore.Fragment.Fragment_payment_cash;
import com.example.app_foodstore.Fragment.Fragment_payment_masterCard;
import com.example.app_foodstore.Fragment.Fragment_payment_momo;
import com.example.app_foodstore.Fragment.Fragment_payment_paypal;
import com.example.app_foodstore.Fragment.Fragment_payment_visa;
import com.example.app_foodstore.Fragment.Fragment_payment_vnPay;

public class ViewPagerPaymentMethodAdapter extends FragmentStateAdapter {
    public ViewPagerPaymentMethodAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_payment_cash();
            case 1:
                return new Fragment_payment_momo();
            case 2:
                return new Fragment_payment_vnPay();
            case 3:
                return new Fragment_payment_visa();
            case 4:
                return new Fragment_payment_masterCard();
            case 5:
                return new Fragment_payment_paypal();
            default:
                return new Fragment_payment_cash();
        }
    }
    @Override
    public int getItemCount() {
        return 6;
    }
}
