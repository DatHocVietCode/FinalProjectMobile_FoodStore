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
    ActivityOrderScreenBinding binding;
    ViewPagerOrderSrceenAdapter adapter;
    OrderViewModel orderViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupTableLayout();
        setupViewPager();
        setupViewModelAndObserver();

        // Giả sử bạn có token user từ đâu đó để load data:
        String token = "user_token_here";
        orderViewModel.loadPendingOrders(token);
        orderViewModel.loadCompleteOrders(token);
    }

    private void setupViewPager() {
        adapter = new ViewPagerOrderSrceenAdapter(getSupportFragmentManager(), getLifecycle());
        binding.orderScreenViewpager.setAdapter(adapter);
        binding.orderScreenViewpager.setOffscreenPageLimit(2);
    }

    private void setupTableLayout() {
        binding.orderScreenTabLayout.addTab(binding.orderScreenTabLayout.newTab().setText("On going"));
        binding.orderScreenTabLayout.addTab(binding.orderScreenTabLayout.newTab().setText("History"));
        binding.orderScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));

        for (int i = 0; i < binding.orderScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.orderScreenTabLayout.getTabAt(i);
            if (tab != null) {
                TextView customTextView = new TextView(OrderScreenActivity.this);
                customTextView.setText(tab.getText());
                Typeface typeface = ResourcesCompat.getFont(OrderScreenActivity.this, R.font.sen);
                customTextView.setTypeface(typeface);
                customTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                customTextView.setTextColor(Color.parseColor("#A5A7B9"));
                customTextView.setGravity(Gravity.CENTER);
                tab.setCustomView(customTextView);
            }
        }

        binding.orderScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#FF7622"));
                }
                binding.orderScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
                binding.orderScreenViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#A5A7B9"));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.orderScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.orderScreenTabLayout.selectTab(binding.orderScreenTabLayout.getTabAt(position));
            }
        });

        // Set màu chữ tab đầu tiên khi mở app
        TabLayout.Tab firstTab = binding.orderScreenTabLayout.getTabAt(0);
        if (firstTab != null) {
            TextView textView = (TextView) firstTab.getCustomView();
            if (textView != null) {
                textView.setTextColor(Color.parseColor("#FF7622"));
            }
        }
    }

    private void setupViewModelAndObserver() {
        APIServiceOrder apiServiceOrder = Constant.retrofit.create(APIServiceOrder.class);
        // Khởi tạo ViewModel (giả sử bạn có factory vì có constructor có tham số)
        orderViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                //noinspection unchecked
                return (T) new OrderViewModel(apiServiceOrder);
            }
        }).get(OrderViewModel.class);

        // Quan sát dữ liệu ongoing
        orderViewModel.getOngoingOrders().observe(this, ongoingOrders -> {
            List<MyOrderPendingDTO> historyOrders = orderViewModel.getHistoryOrders().getValue();
            adapter.setData(ongoingOrders, historyOrders);
        });

        // Quan sát dữ liệu history
        orderViewModel.getHistoryOrders().observe(this, historyOrders -> {
            List<MyOrderPendingDTO> ongoingOrders = orderViewModel.getOngoingOrders().getValue();
            adapter.setData(ongoingOrders, historyOrders);
        });
    }
}
