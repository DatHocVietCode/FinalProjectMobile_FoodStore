package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.CategoryAdapter;
import com.example.app_foodstore.Fragment.Fragment_BottomNavigation;
import com.example.app_foodstore.Fragment.Fragment_SearchBar;
import com.example.app_foodstore.Fragment.Fragment_btn_cart;
import com.example.app_foodstore.Fragment.Fragment_foodDisplay1;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.CateViewModel;
import com.example.app_foodstore.ViewModel.FoodViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreenActivity extends AppCompatActivity {
    FoodViewModel foodViewModel;
    CateViewModel cateViewModel;
    RecyclerView category_rv;
    NestedScrollView nestedScrollView;
    Fragment_BottomNavigation bottomNavigationFragment;
    Fragment_SearchBar searchBarFragment;
    CircleImageView ms_header_avatar;
    List<FoodModel> newFood;
    List<CategoryModel> categoryModels;
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
        iniViewModel();
        includeFragments();
        setupCart();
        setupRcCategory();
        setupScrollView();
        setupAvartar();
        setupSeeAll();
    }

    private void iniViewModel() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        cateViewModel = new ViewModelProvider(this).get(CateViewModel.class);
    }

    private void setupSeeAll() {
        LinearLayout ln_seeAllCate = findViewById(R.id.ms_seeAllCate);
        ln_seeAllCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ln_seeAllBestSellerFood = findViewById(R.id.ms_seeAllBestSeller);
        ln_seeAllBestSellerFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, MenuActivity.class);
                intent.putExtra("target_tab", 1);
                startActivity(intent);
            }
        });

        LinearLayout ln_seeAllNewFood = findViewById(R.id.ms_seeAllNewFood);
        ln_seeAllNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, MenuActivity.class);
                intent.putExtra("target_tab", 2);
                startActivity(intent);
            }
        });
    }

    private void setupAvartar() {
        ms_header_avatar = findViewById(R.id.ms_header_avatar);
        ms_header_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupScrollView() {
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

    private void setupRcCategory() {
        category_rv = findViewById(R.id.ms_rv_category);
        cateViewModel.getAllCate().observe(this, new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> listCate) {
                if (listCate != null) {
                    categoryModels = listCate;
                    Log.d("Catee", "onChanged: " + categoryModels.size());
                    CategoryAdapter categoryAdapter = new CategoryAdapter(HomeScreenActivity.this,categoryModels);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeScreenActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    category_rv.setLayoutManager(layoutManager);
                    category_rv.setAdapter(categoryAdapter);
                }
            }
        });
    }

    private void setupCart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment_btn_cart btn_cart = new Fragment_btn_cart();
        transaction.replace(R.id.ms_fragment_container_btn_cart, btn_cart);
        transaction.commit();
        int cart_count = 3; // Lấy số sản phẩm trong giỏ hàng từ api
        if (btn_cart != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                btn_cart.updateCartNotify(cart_count);
            });
        }
    }

    private void includeFragments() {
        includeBestSellerFood();
        includeNewFood();
    }

    private void includeNewFood() {
        foodViewModel.getNewFoodList("", null, "", "").observe(this, foodList -> {
            if (foodList != null && !foodList.isEmpty()) {
                newFood = foodList;

                FoodModel firstFood = newFood.get(0);
                Long id = firstFood.getId();
                String name = firstFood.getName();
                Float price = firstFood.getPrice();
                Long cateId = firstFood.getCategory_id();

                if (cateId != null) {
                    cateViewModel.getCateById(cateId).observe(this, category -> {
                        if (category != null) {
                            // Chuẩn bị Bundle
                            Fragment_foodDisplay1 fragment = new Fragment_foodDisplay1();
                            Bundle bundle = new Bundle();
                            bundle.putString("food_id", id != null ? id.toString() : "default_value");
                            bundle.putString("food_name", name);
                            bundle.putFloat("food_price", price);
                            bundle.putString("food_cateId", cateId.toString());
                            bundle.putString("food_cateName", category.get(0).getName()); // hoặc thông tin khác từ Category

                            fragment.setArguments(bundle);

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.ms_newFood, fragment)
                                    .commit();
                        } else {
                            Log.w("includeNewFood", "Category is null");
                        }
                    });
                } else {
                    Log.w("includeNewFood", "CateId is null");
                }
            } else {
                Log.w("includeNewFood", "Food list is null or empty");
            }
        });
    }

    private void includeBestSellerFood() {
        foodViewModel.getBestSellerFoodList("", null, "", "").observe(this, foodList -> {
            if (foodList != null && !foodList.isEmpty()) {
                newFood = foodList;
                FoodModel firstFood = newFood.get(0);
                Long id = firstFood.getId();
                String name = firstFood.getName();
                Float price = firstFood.getPrice();
                Long cateId = firstFood.getCategory_id();
                Log.d("category", "includeBestSellerFood: " + cateId + name);
                if (cateId != null) {
                    cateViewModel.getCateById(cateId).observe(this, category -> {
                        if (category != null) {
                            Log.d("category", "includeBestSellerFood: " + category.get(0).getName());
                            Fragment_foodDisplay1 fragment = new Fragment_foodDisplay1();
                            Bundle bundle = new Bundle();
                            bundle.putString("food_id", id != null ? id.toString() : "default_value");
                            bundle.putString("food_name", name);
                            bundle.putFloat("food_price", price);
                            bundle.putString("food_cateId", cateId.toString());
                            bundle.putString("food_cateName", category.get(0).getName());

                            fragment.setArguments(bundle);

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.ms_bestSellerFood, fragment)
                                    .commit();
                        } else {
                            Log.w("includeBestSellerFood", "Category is null");
                        }
                    });
                } else {
                    Log.w("includeBestSellerFood", "CateId is null");
                }
            } else {
                Log.w("includeBestSellerFood", "Food list is null or empty");
            }
        });
    }

}
