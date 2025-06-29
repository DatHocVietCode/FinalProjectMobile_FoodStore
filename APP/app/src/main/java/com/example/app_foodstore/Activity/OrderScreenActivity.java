package com.example.app_foodstore.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.Order.APIServiceOrder;
import com.example.app_foodstore.Adapter.ViewPagerOrderSrceenAdapter;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.OrderViewModel;
import com.example.app_foodstore.databinding.ActivityOrderScreenBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class OrderScreenActivity extends AppCompatActivity {
    private ActivityOrderScreenBinding binding;
    private ViewPagerOrderSrceenAdapter adapter;
    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnhXa();


        // Giờ gọi load data sau khi ViewModel đã khởi tạo
        String token = "user_token_here"; // TODO: Thay bằng token thực tế
        orderViewModel.loadPendingOrders();
        orderViewModel.loadCompleteOrders();
    }

    private void AnhXa() {
        setupTabLayout();
        setupViewPager();
        setupViewModelAndObservers();
    }

    private void setupViewPager() {
        adapter = new ViewPagerOrderSrceenAdapter(getSupportFragmentManager(), getLifecycle());
        binding.orderScreenViewpager.setAdapter(adapter);
        binding.orderScreenViewpager.setOffscreenPageLimit(2);

        // Đồng bộ ViewPager2 với TabLayout
        binding.orderScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.orderScreenTabLayout.selectTab(binding.orderScreenTabLayout.getTabAt(position));
            }
        });
    }

    private void setupTabLayout() {
        // Thêm tab
        binding.orderScreenTabLayout.addTab(binding.orderScreenTabLayout.newTab().setText("On going"));
        binding.orderScreenTabLayout.addTab(binding.orderScreenTabLayout.newTab().setText("History"));
        binding.orderScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));

        // Tạo custom view cho tab để chỉnh font, size, màu chữ mặc định
        for (int i = 0; i < binding.orderScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.orderScreenTabLayout.getTabAt(i);
            if (tab != null) {
                TextView customTextView = new TextView(this);
                customTextView.setText(tab.getText());
                Typeface typeface = ResourcesCompat.getFont(this, R.font.sen);
                customTextView.setTypeface(typeface);
                customTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                customTextView.setTextColor(Color.parseColor("#A5A7B9")); // màu chữ không chọn
                customTextView.setGravity(Gravity.CENTER);
                tab.setCustomView(customTextView);
            }
        }

        // Thiết lập listener cho tab thay đổi màu chữ và chuyển ViewPager2
        binding.orderScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#FF7622")); // màu chữ tab chọn
                }
                binding.orderScreenViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#A5A7B9")); // màu chữ tab không chọn
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không làm gì
            }
        });

        // Set màu chữ tab đầu tiên khi mở app
        TabLayout.Tab firstTab = binding.orderScreenTabLayout.getTabAt(0);
        if (firstTab != null && firstTab.getCustomView() instanceof TextView) {
            ((TextView) firstTab.getCustomView()).setTextColor(Color.parseColor("#FF7622"));
        }
    }

    private void setupViewModelAndObservers() {
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Quan sát dữ liệu
        orderViewModel.getOngoingOrders().observe(this, data -> updateAdapter());
        orderViewModel.getHistoryOrders().observe(this, data -> updateAdapter());
    }

    private void updateAdapter() {
        List<MyOrderPendingDTO> ongoingOrders = orderViewModel.getOngoingOrders().getValue();
        List<MyOrderPendingDTO> historyOrders = orderViewModel.getHistoryOrders().getValue();

        if (ongoingOrders != null && historyOrders != null) {
            adapter.setData(ongoingOrders, historyOrders);
            adapter.notifyDataSetChanged();
        }
    }
}
