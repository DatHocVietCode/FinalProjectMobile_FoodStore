package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.PopularFoodAdapter_rc;
import com.example.app_foodstore.Adapter.SearchKeywordAdapter;
import com.example.app_foodstore.Adapter.SuggestedFoodAdapter;
import com.example.app_foodstore.Fragment.Fragment_BottomNavigation;
import com.example.app_foodstore.Fragment.Fragment_SearchBar;
import com.example.app_foodstore.Fragment.Fragment_btn_cart;
import com.example.app_foodstore.Indicator.DotPagerIndicatorDecoration;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Model.SearchKeywordModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    Fragment_BottomNavigation bottomNavigationFragment;
    Fragment_SearchBar searchBarFragment;
    RecyclerView rc_recent_keyword, rc_suggestedFood;
    List<SearchKeywordModel> recentKeywords;
    RecyclerView rc_popularFood;
    List<FoodModel> foods;
    PopularFoodAdapter_rc gv_adapter;
    NestedScrollView nestedScrollView;
    FoodViewModel foodViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            bottomNavigationFragment = new Fragment_BottomNavigation();
            searchBarFragment = new Fragment_SearchBar();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ss_fragment_bottomNavigation_container, bottomNavigationFragment)
                    .replace(R.id.ss_fragment_searchBar_container, searchBarFragment)
                    .runOnCommit(() -> {
                        // Fragment đã được commit và View đã sẵn sàng
                        EditText editText = searchBarFragment.getSearchEditText();
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        LinearLayout searchBar = searchBarFragment.getSearchBar();
                        searchBar.setFocusable(false);
                        searchBar.setClickable(false);
                    })
                    .commit();
        } else {
            bottomNavigationFragment = (Fragment_BottomNavigation) getSupportFragmentManager()
                    .findFragmentById(R.id.ss_fragment_bottomNavigation_container);
            searchBarFragment = (Fragment_SearchBar) getSupportFragmentManager()
                    .findFragmentById(R.id.ss_fragment_searchBar_container);
        }
        AnhXa();
    }
    private void AnhXa() {
        initViewModel();
        setupRcKeyword();
        setupRcPopularFood();
        setupRcSuggestedFood();
        setupBtnCart();
    }

    private void initViewModel() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupBtnCart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment_btn_cart btn_cart = new Fragment_btn_cart();
        transaction.replace(R.id.ss_fragment_container_btn_cart, btn_cart);
        transaction.commit();
        int cart_count = 3; // Lấy số sản phẩm trong giỏ hàng từ api
        if (btn_cart != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                btn_cart.updateCartNotify(cart_count);
            });
        }
    }

    private void setupRcSuggestedFood() {
        rc_suggestedFood = findViewById(R.id.ss_rc_suggestedFood);
        foodViewModel.getNewFoodList("", null
        ,null, null).observe(this, foods->
        {
            if (foods != null) {
                SuggestedFoodAdapter adapter = new SuggestedFoodAdapter(this, foods);
                rc_suggestedFood.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rc_suggestedFood.setLayoutManager(layoutManager);
                rc_suggestedFood.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


    }

    private void setupRcPopularFood() {
        rc_popularFood = findViewById(R.id.ss_rv_popularFoods);
        foodViewModel.getBestSellerFoodList("", null, null, null)
                .observe(this, foods -> {
                            if (foods != null) {
                                gv_adapter = new PopularFoodAdapter_rc(this, foods);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                                        2, GridLayoutManager.HORIZONTAL, false);
                                rc_popularFood.setLayoutManager(gridLayoutManager);
                                rc_popularFood.setAdapter(gv_adapter);
                                rc_popularFood.addItemDecoration(new DotPagerIndicatorDecoration(this,4, RecyclerView.HORIZONTAL));
                                gv_adapter.notifyDataSetChanged();
                            }
                        }
                );
        nestedScrollView = findViewById(R.id.ss_nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int previousScrollY = 0;
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > previousScrollY) {
                    bottomNavigationFragment.hideBottomNavigation();
                } else if (scrollY < previousScrollY) {
                    // Cuộn lên -> hiển thị BottomNavigationView
                    bottomNavigationFragment.showBottomNavigation();
                }
                previousScrollY = scrollY;
            }
        });
    }

    private void setupRcKeyword() {
        rc_recent_keyword = findViewById(R.id.ss_rc_recent_keyword);
        recentKeywords = new ArrayList<>();
        recentKeywords.add(new SearchKeywordModel("Burger"));
        recentKeywords.add(new SearchKeywordModel("Pizza"));
        recentKeywords.add(new SearchKeywordModel("Juice"));
        recentKeywords.add(new SearchKeywordModel("Pasta"));
        SearchKeywordAdapter rc_adapter = new SearchKeywordAdapter(this, recentKeywords);
        rc_recent_keyword.setAdapter(rc_adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_recent_keyword.setLayoutManager(layoutManager);
        rc_adapter.setOnKeywordClickListener(new SearchKeywordAdapter.OnKeywordClickListener() {
            @Override
            public void onKeywordClick(String keyword) {
                // Đặt text từ keyword vào ô tìm kiếm
                searchBarFragment.getSearchEditText().setText(keyword);

                // Đưa con trỏ (cursor) về cuối chữ
                searchBarFragment.getSearchEditText().setSelection(keyword.length());
            }
        });
        rc_adapter.notifyDataSetChanged();
    }
}
