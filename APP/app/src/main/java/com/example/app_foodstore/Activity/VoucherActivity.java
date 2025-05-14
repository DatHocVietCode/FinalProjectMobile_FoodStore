package com.example.app_foodstore.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.ViewpagerVoucherAdapter;
import com.example.app_foodstore.R;
import com.example.app_foodstore.databinding.ActivityVoucherBinding;
import com.google.android.material.tabs.TabLayout;

public class VoucherActivity extends AppCompatActivity {
    ActivityVoucherBinding binding;
    ViewpagerVoucherAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeUI();
    }

    private void initializeUI() {
        setupTabLayout();
        setupViewPager();
    }

    private void setupTabLayout() {
        addTabsToTabLayout();
        customizeTabs();
        setTabSelectedListener();
    }

    private void addTabsToTabLayout() {
        binding.voucherScreenTabLayout.addTab(binding.voucherScreenTabLayout.newTab().setText("My Vouchers"));
        binding.voucherScreenTabLayout.addTab(binding.voucherScreenTabLayout.newTab().setText("Available Vouchers"));
    }

    private void customizeTabs() {
        for (int i = 0; i < binding.voucherScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.voucherScreenTabLayout.getTabAt(i);
            if (tab != null) {
                // Create custom TextView
                TextView customTextView = new TextView(VoucherActivity.this);
                customTextView.setText(tab.getText());
                customTextView.setTypeface(ResourcesCompat.getFont(VoucherActivity.this, R.font.sen));
                customTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                customTextView.setTextColor(Color.parseColor("#A5A7B9"));
                customTextView.setGravity(Gravity.CENTER);
                tab.setCustomView(customTextView);
            }
        }
    }

    private void setTabSelectedListener() {
        binding.voucherScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabTextColor(tab, "#FF7622"); // Set selected tab color
                binding.voucherScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
                binding.voucherScreenViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextColor(tab, "#A5A7B9"); // Set unselected tab color
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle reselection
            }
        });

        // Sync ViewPager with TabLayout
        binding.voucherScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.voucherScreenTabLayout.selectTab(binding.voucherScreenTabLayout.getTabAt(position));
            }
        });
    }

    private void updateTabTextColor(TabLayout.Tab tab, String color) {
        TextView textView = (TextView) tab.getCustomView();
        if (textView != null) {
            textView.setTextColor(Color.parseColor(color));
        }
    }

    private void setupViewPager() {
        adapter = new ViewpagerVoucherAdapter(getSupportFragmentManager(), getLifecycle()); // Không truyền tabNum nữa
        binding.voucherScreenViewpager.setAdapter(adapter);
        binding.voucherScreenViewpager.setOffscreenPageLimit(2);  // Giữ 2 tab
        adapter.notifyDataSetChanged();
    }


    private void setInitialTabTextColor() {
        TabLayout.Tab firstTab = binding.voucherScreenTabLayout.getTabAt(0);
        if (firstTab != null) {
            updateTabTextColor(firstTab, "#FF7622"); // Set the color for the first tab
        }
    }
}
