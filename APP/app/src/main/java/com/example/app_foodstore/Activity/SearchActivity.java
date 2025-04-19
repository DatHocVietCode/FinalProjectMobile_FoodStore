package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.PopularFoodAdapter_rc;
import com.example.app_foodstore.Adapter.SearchKeywordAdapter;
import com.example.app_foodstore.Fragment.Fragment_BottomNavigation;
import com.example.app_foodstore.Fragment.Fragment_SearchBar;
import com.example.app_foodstore.Indicator.DotPagerIndicatorDecoration;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Model.SearchKeywordModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    Fragment_BottomNavigation bottomNavigationFragment;
    Fragment_SearchBar searchBarFragment;

    RecyclerView rc_recent_keyword;
    List<SearchKeywordModel> recentKeywords;
    RecyclerView rc_popularFood;
    List<FoodModel> foods;
    PopularFoodAdapter_rc gv_adapter;
    NestedScrollView nestedScrollView;
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
        rc_recent_keyword = findViewById(R.id.ss_rc_recent_keyword);
        recentKeywords = new ArrayList<>();
        recentKeywords.add(new SearchKeywordModel("Bánh canh"));
        recentKeywords.add(new SearchKeywordModel("Hủ tiếu"));
        recentKeywords.add(new SearchKeywordModel("Nước ngọt"));
        recentKeywords.add(new SearchKeywordModel("Bún chả"));
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

        rc_popularFood = findViewById(R.id.ss_rv_popularFoods);
        foods = new ArrayList<>();
        foods.add(new FoodModel("Cate 1", "Món ăn 1",1));
        foods.add(new FoodModel("Cate 2", "Món ăn 2",1));
        foods.add(new FoodModel("Cate 3", "Món ăn 3",1));
        foods.add(new FoodModel("Cate 4", "Món ăn 4",1));
        foods.add(new FoodModel("Cate 5", "Món ăn 5",1));
        foods.add(new FoodModel("Cate 6", "Món ăn 6",1));
        foods.add(new FoodModel("Cate 7", "Món ăn 7",1));
        foods.add(new FoodModel("Cate 8", "Món ăn 8",1));

        gv_adapter = new PopularFoodAdapter_rc(this, foods);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.HORIZONTAL, false);
        rc_popularFood.setLayoutManager(gridLayoutManager);
        rc_popularFood.setAdapter(gv_adapter);
        rc_popularFood.addItemDecoration(new DotPagerIndicatorDecoration(this,4, RecyclerView.HORIZONTAL));

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
}
