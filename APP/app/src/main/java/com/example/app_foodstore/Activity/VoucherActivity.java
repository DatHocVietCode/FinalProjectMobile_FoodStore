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

        AnhXa();
    }

    private void AnhXa() {
        setupTabLayout();
        setupViewPager();
    }

    private void setupTabLayout() {
        binding.voucherScreenTabLayout.addTab(binding.voucherScreenTabLayout.newTab().setText("My Vouchers"));
        binding.voucherScreenTabLayout.addTab(binding.voucherScreenTabLayout.newTab().setText("Available Vouchers"));
        binding.voucherScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
        for (int i = 0; i < binding.voucherScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.voucherScreenTabLayout.getTabAt(i);
            if (tab != null) {
                // Tạo custom TextView
                TextView customTextView = new TextView(VoucherActivity.this);
                customTextView.setText(tab.getText());
                // Set font từ thư mục res/font/sen.ttf (sen.ttf đã thêm vào project)
                Typeface typeface = ResourcesCompat.getFont(VoucherActivity.this, R.font.sen);
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
        binding.voucherScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#FF7622")); // màu khi chọn
                }
                binding.voucherScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
                binding.voucherScreenViewpager.setCurrentItem(tab.getPosition());
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
        binding.voucherScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.voucherScreenTabLayout.selectTab(binding.voucherScreenTabLayout.getTabAt(position));
                Log.d("position", "onPageSelected: " + position);
            }
        });

        // Set indicator màu cam
        binding.voucherScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
        // Set màu chữ cho tab đầu tiên khi mở app
        TabLayout.Tab firstTab = binding.voucherScreenTabLayout.getTabAt(0);
        if (firstTab != null) {
            TextView textView = (TextView) firstTab.getCustomView();
            if (textView != null) {
                textView.setTextColor(Color.parseColor("#FF7622")); // chữ cam
            }
        }
    }

    private void setupViewPager() {
        adapter = new ViewpagerVoucherAdapter(getSupportFragmentManager(), getLifecycle());
        binding.voucherScreenViewpager.setAdapter(adapter);
        binding.voucherScreenViewpager.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();
    }
}
