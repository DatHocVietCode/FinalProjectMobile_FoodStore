package com.example.app_foodstore.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_foodstore.Fragment.Fragment_btn_filter;
import com.example.app_foodstore.R;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        AnhXa();
    }

    private void AnhXa() {
        setupBtnFilter();
    }

    private void setupBtnFilter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment_btn_filter btn_filter = new Fragment_btn_filter();
        transaction.replace(R.id.menu_screen_fragmentContainer_btnFilter, btn_filter);
        transaction.commit();
    }
}
