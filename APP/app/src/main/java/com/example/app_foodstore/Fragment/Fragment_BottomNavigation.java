package com.example.app_foodstore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Activity.HomeScreenActivity;
import com.example.app_foodstore.Activity.OrderScreenActivity;
import com.example.app_foodstore.Activity.ReservateScreenActivity;
import com.example.app_foodstore.Activity.SettingActivity;
import com.example.app_foodstore.Activity.VoucherActivity;
import com.example.app_foodstore.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class Fragment_BottomNavigation extends Fragment {
    private View rootView;
    private BottomAppBar bottomAppBar;
    private CoordinatorLayout coordinatorLayout;
    public Fragment_BottomNavigation() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomAppBar = view.findViewById(R.id.bottomNavigation);

        view.findViewById(R.id.bottomNavigation_btnHome).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), HomeScreenActivity.class));
        });

        view.findViewById(R.id.bottomNavigation_btnMyOrders).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), OrderScreenActivity.class));
        });
        view.findViewById(R.id.bottomNavigation_btnVoucher).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), VoucherActivity.class));
        });

        view.findViewById(R.id.fabAdd).setOnClickListener(
                v -> {
                    Intent intent = new Intent(getContext(), ReservateScreenActivity.class);
                    //intent.putExtra("isReservate", true);
                    startActivity(intent);
                }
        );
        view.findViewById(R.id.bottomNavigation_settingBtn).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingActivity.class));
        });
    }

    public void hideBottomNavigation() {
        /*if (bottomAppBar != null) {
            bottomAppBar.animate()
                    .translationY(bottomAppBar.getHeight())
                    .setDuration(150)
                    .start();
        }*/
        if (rootView != null)
        {

            rootView.animate()
                    .translationY(bottomAppBar.getHeight())
                    .setDuration(150)
                    .start();
        }
    }

    public void showBottomNavigation() {
       /* if (bottomAppBar != null) {
            bottomAppBar.animate()
                    .translationY(0)
                    .setDuration(150)
                    .start();
        }*/
        if (rootView != null)
        {

            rootView.animate()
                    .translationY(0)
                    .setDuration(150)
                    .start();
        }
    }
}

