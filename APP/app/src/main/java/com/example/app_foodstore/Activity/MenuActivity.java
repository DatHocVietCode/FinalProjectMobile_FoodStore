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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.ViewPagerFoodTabLayoutAdapter;
import com.example.app_foodstore.Fragment.Fragment_btn_filter;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.FoodViewModel;
import com.example.app_foodstore.databinding.ActivityMenuBinding;
import com.google.android.material.tabs.TabLayout;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;
    ViewPagerFoodTabLayoutAdapter adapter;
    FoodViewModel foodViewModel;
    String keyword;
    Long categoryId;
    String sortByName;
    String sortByPrice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AnhXa();

        int targetTab = getIntent().getIntExtra("target_tab", 0); // 0 là mặc định nếu không truyền
        binding.menuScreenViewpager.setCurrentItem(targetTab, false);

        // Đồng bộ luôn TabLayout:
        TabLayout.Tab tab = binding.menuScreenTabLayout.getTabAt(targetTab);
        if (tab != null) {
            tab.select();
        }
    }

    private void AnhXa() {
        iniViewModel();
        getKeyWord();
        setupBtnFilter();
        setupViewpager();
        setupTableLayout();
    }

    private void getKeyWord() {
        // Lấy dữ liệu từ Intent và đảm bảo có giá trị mặc định nếu không có
        keyword = getIntent().getStringExtra("keyword");
        if (keyword == null) {
            keyword = "";  // Giá trị mặc định cho keyword
        }
        categoryId = getIntent().getLongExtra("categoryId", -1L);  // Dùng -1 làm default nếu không có giá trị
        if (categoryId == -1L) {
            categoryId = null;  // Nếu không có categoryId, truyền null để API xử lý
        }
        sortByName = getIntent().getStringExtra("sortByName");
        if (sortByName == null) {
            sortByName = "";  // Giá trị mặc định cho sortByName
        }
        sortByPrice = getIntent().getStringExtra("sortByPrice");
        if (sortByPrice == null) {
            sortByPrice = "";  // Giá trị mặc định cho sortByPrice
        }
    }

    private void iniViewModel() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupViewpager() {
        adapter = new ViewPagerFoodTabLayoutAdapter(getSupportFragmentManager(), getLifecycle()
                , keyword, categoryId, sortByName, sortByPrice);
        binding.menuScreenViewpager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupBtnFilter() {
        Bundle bundle = new Bundle();
        if (categoryId != null)
        {
            bundle.putLong("categoryId", categoryId);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment_btn_filter btn_filter = new Fragment_btn_filter();
        btn_filter.setArguments(bundle);
        transaction.replace(R.id.menu_screen_fragmentContainer_btnFilter, btn_filter);
        transaction.commit();
    }
    private void setupTableLayout() {
        binding.menuScreenTabLayout.addTab(binding.menuScreenTabLayout.newTab().setText("All Foods"));
        binding.menuScreenTabLayout.addTab(binding.menuScreenTabLayout.newTab().setText("Best Seller!"));
        binding.menuScreenTabLayout.addTab(binding.menuScreenTabLayout.newTab().setText("New Food!"));
        binding.menuScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
        for (int i = 0; i < binding.menuScreenTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.menuScreenTabLayout.getTabAt(i);
            if (tab != null) {
                // Tạo custom TextView
                TextView customTextView = new TextView(MenuActivity.this);
                customTextView.setText(tab.getText());
                // Set font từ thư mục res/font/sen.ttf (sen.ttf đã thêm vào project)
                Typeface typeface = ResourcesCompat.getFont(MenuActivity.this, R.font.sen);
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
        binding.menuScreenTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextColor(Color.parseColor("#FF7622")); // màu khi chọn
                }
                binding.menuScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
                binding.menuScreenViewpager.setCurrentItem(tab.getPosition());
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
        binding.menuScreenViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.menuScreenTabLayout.selectTab(binding.menuScreenTabLayout.getTabAt(position));
                Log.d("position", "onPageSelected: " + position);
            }
        });

        // Set indicator màu cam
        binding.menuScreenTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF7622"));
        // Set màu chữ cho tab đầu tiên khi mở app
        TabLayout.Tab firstTab = binding.menuScreenTabLayout.getTabAt(0);
        if (firstTab != null) {
            TextView textView = (TextView) firstTab.getCustomView();
            if (textView != null) {
                textView.setTextColor(Color.parseColor("#FF7622")); // chữ cam
            }
        }
    }
}
