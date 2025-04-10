package com.example.app_foodstore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Activity.HomeScreenActivity;
import com.example.app_foodstore.Activity.OrderScreenActivity;
import com.example.app_foodstore.Activity.SeatScreenActivity;
import com.example.app_foodstore.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fragment_BottomNavigation extends Fragment {
    private View rootView;
    private BottomAppBar bottomAppBar;

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

        view.findViewById(R.id.bottomNavigation_btnSeat).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SeatScreenActivity.class));
        });
    }

    public void hideBottomNavigation() {
        if (bottomAppBar != null) {
            bottomAppBar.animate()
                    .translationY(bottomAppBar.getHeight())
                    .setDuration(150)
                    .start();
        }
    }

    public void showBottomNavigation() {
        if (bottomAppBar != null) {
            bottomAppBar.animate()
                    .translationY(0)
                    .setDuration(150)
                    .start();
        }
    }
}

