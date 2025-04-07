package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.CategoryAdapter;
import com.example.app_foodstore.Fragment.Fragment_BottomNavigation;
import com.example.app_foodstore.Fragment.Fragment_SearchBar;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {
    TextView tv_notify;
    RecyclerView category_rv;
    NestedScrollView nestedScrollView;
    Fragment_BottomNavigation bottomNavigationFragment;
    Fragment_SearchBar searchBarFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        if (savedInstanceState == null) {
            bottomNavigationFragment = new Fragment_BottomNavigation();
            searchBarFragment = new Fragment_SearchBar();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ms_fragment_bottomNavigation_container, bottomNavigationFragment)
                    .replace(R.id.ms_fragment_searchBar_container, searchBarFragment)
                    .commit();
        } else {
            bottomNavigationFragment = (Fragment_BottomNavigation) getSupportFragmentManager()
                    .findFragmentById(R.id.ms_fragment_bottomNavigation_container);
            searchBarFragment = (Fragment_SearchBar) getSupportFragmentManager()
                    .findFragmentById(R.id.ms_fragment_searchBar_container);
        }

        SetUp();

    }
    private void SetUp() {
        tv_notify = findViewById(R.id.ms_header_cart_notify);
        int cart_count = 3; // Lấy số sản phẩm trong giỏ hàng từ api
        if (cart_count > 0)
        {
            tv_notify.setVisibility(View.VISIBLE);
            tv_notify.setText("4");
        }
        else
        {
            tv_notify.setVisibility(View.INVISIBLE);
        }

        category_rv = findViewById(R.id.ms_rv_category);
        List<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel(1, "Pizza", "R.drawable.pizza"));
        categoryModels.add(new CategoryModel(2, "Pizza1", "R.drawable.pizza"));
        categoryModels.add(new CategoryModel(3, "Pizza2", "R.drawable.pizza"));
        categoryModels.add(new CategoryModel(4, "Pizza3", "R.drawable.pizza"));
        categoryModels.add(new CategoryModel(5, "Pizza4", "R.drawable.pizza"));
        CategoryAdapter categoryAdapter = new CategoryAdapter(HomeScreenActivity.this,categoryModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        category_rv.setLayoutManager(layoutManager);
        category_rv.setAdapter(categoryAdapter);


        LinearLayout ln_viewSeeAll;
        ln_viewSeeAll = findViewById(R.id.ms_seeAllCate);
        nestedScrollView = findViewById(R.id.ms_nestedScrollView);

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
