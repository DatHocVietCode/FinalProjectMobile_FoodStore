package com.example.app_foodstore.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.ViewPagerOrderSrceenAdapter;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.databinding.ActivityOrderScreenBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderScreenActivity extends AppCompatActivity {
    ActivityOrderScreenBinding binding;
    ViewPagerOrderSrceenAdapter adapter;
    ViewPager2 viewpager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnhXa();
    }

    private void AnhXa() {
        setupTableLayout();
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new ViewPagerOrderSrceenAdapter(getSupportFragmentManager(), getLifecycle());
        binding.orderScreenViewpager.setAdapter(adapter);
    }

    private void setupTableLayout() {
        binding.orderScreenTabLayout.addTab(binding.orderScreenTabLayout.newTab().setText("On going"));
        binding.orderScreenTabLayout.addTab(binding.orderScreenTabLayout.newTab().setText("History"));
        binding.orderScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
        for (int i = 0; i < binding.orderScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.orderScreenTabLayout.getTabAt(i);
            if (tab != null) {
                // Tạo custom TextView
                TextView customTextView = new TextView(OrderScreenActivity.this);
                customTextView.setText(tab.getText());
                // Set font từ thư mục res/font/sen.ttf (sen.ttf đã thêm vào project)
                Typeface typeface = ResourcesCompat.getFont(OrderScreenActivity.this, R.font.sen);
                customTextView.setTypeface(typeface);
                // Set size và màu
                customTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                customTextView.setTextColor(Color.parseColor("#A5A7B9"));
                // Căn giữa text trong tab
                customTextView.setGravity(Gravity.CENTER);
                // Gán lại vào tab
                tab.setCustomView(customTextView);
            }
        }
        binding.orderScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#FF7622")); // màu khi chọn
                }
                binding.orderScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
                binding.orderScreenViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#A5A7B9")); // màu khi không chọn
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý gì thêm nếu không muốn
            }

        });
        binding.orderScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.orderScreenTabLayout.selectTab(binding.orderScreenTabLayout.getTabAt(position));
                Log.d("position", "onPageSelected: " + position);
            }
        });

        // Set indicator màu cam
        binding.orderScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
        // Set màu chữ cho tab đầu tiên khi mở app
        TabLayout.Tab firstTab = binding.orderScreenTabLayout.getTabAt(0);
        if (firstTab != null) {
            TextView textView = (TextView) firstTab.getCustomView();
            if (textView != null) {
                textView.setTextColor(Color.parseColor("#FF7622")); // chữ cam
            }
        }
    }
}
