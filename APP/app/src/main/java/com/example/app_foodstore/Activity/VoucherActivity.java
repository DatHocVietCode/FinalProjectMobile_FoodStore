package com.example.app_foodstore.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.ViewpagerVoucherAdapter;
import com.example.app_foodstore.Fragment.Fragment_voucher_rc;
import com.example.app_foodstore.R;
import com.example.app_foodstore.databinding.ActivityVoucherBinding;
import com.google.android.material.tabs.TabLayout;

public class VoucherActivity extends AppCompatActivity {
    private ActivityVoucherBinding binding;
    private ViewpagerVoucherAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupTabLayout();
        setupViewPager();
        setTabSelectedListener();
        setInitialTabTextColor();
    }

    private void setupTabLayout() {
        binding.voucherScreenTabLayout.addTab(binding.voucherScreenTabLayout.newTab().setText("My Vouchers"));
        binding.voucherScreenTabLayout.addTab(binding.voucherScreenTabLayout.newTab().setText("Available Vouchers"));

        for (int i = 0; i < binding.voucherScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.voucherScreenTabLayout.getTabAt(i);
            if (tab != null) {
                TextView customTextView = new TextView(this);
                customTextView.setText(tab.getText());
                customTextView.setTypeface(ResourcesCompat.getFont(this, R.font.sen));
                customTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                customTextView.setTextColor(Color.parseColor("#A5A7B9"));
                customTextView.setGravity(Gravity.CENTER);
                tab.setCustomView(customTextView);
            }
        }
    }

    private void setupViewPager() {
        adapter = new ViewpagerVoucherAdapter(getSupportFragmentManager(), getLifecycle());
        binding.voucherScreenViewpager.setAdapter(adapter);
        binding.voucherScreenViewpager.setOffscreenPageLimit(2);
    }

    private void setTabSelectedListener() {
        binding.voucherScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabTextColor(tab, "#FF7622");
                binding.voucherScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
                int position = tab.getPosition();
                binding.voucherScreenViewpager.setCurrentItem(position);

                // Lấy fragment tương ứng và gọi reloadData()
                Fragment_voucher_rc fragment = adapter.getFragment(position);
                if (fragment != null) {
                    fragment.reloadData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextColor(tab, "#A5A7B9");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Có thể reload lại dữ liệu nếu muốn
            }
        });

        binding.voucherScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.voucherScreenTabLayout.selectTab(binding.voucherScreenTabLayout.getTabAt(position));
            }
        });
    }

    private void updateTabTextColor(TabLayout.Tab tab, String colorHex) {
        TextView textView = (TextView) tab.getCustomView();
        if (textView != null) {
            textView.setTextColor(Color.parseColor(colorHex));
        }
    }

    private void setInitialTabTextColor() {
        TabLayout.Tab firstTab = binding.voucherScreenTabLayout.getTabAt(0);
        if (firstTab != null) {
            updateTabTextColor(firstTab, "#FF7622");
        }
    }
}
