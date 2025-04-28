package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.app_foodstore.API.APIClient;
import com.example.app_foodstore.API.APIService;
import com.example.app_foodstore.Adapter.CategoryAdapter;
import com.example.app_foodstore.CategoryResponse;
import com.example.app_foodstore.Fragment.Fragment_BottomNavigation;
import com.example.app_foodstore.Fragment.Fragment_SearchBar;
import com.example.app_foodstore.Fragment.Fragment_foodDisplay1;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenActivity extends AppCompatActivity {
    TextView tv_notify;
    RecyclerView category_rv;
    NestedScrollView nestedScrollView;
    Fragment_BottomNavigation bottomNavigationFragment;
    Fragment_SearchBar searchBarFragment;
    CircleImageView ms_header_avatar;
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
        includeFragments();
        setupCart();
        setupRcCategory();
        setupScrollView();
        setupAvartar();
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


    // 3. Và sửa lại trong setupRcCategory:
    private void setupRcCategory() {
        category_rv = findViewById(R.id.ms_rv_category);

        APIService apiService = APIClient.getService();
        // Note: getAllCategories() now returns Call<CategoryResponse>
        apiService.getAllCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryModel> categoryModels = response.body().getData();
                    CategoryAdapter categoryAdapter =
                            new CategoryAdapter(HomeScreenActivity.this, categoryModels);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(HomeScreenActivity.this,
                                    LinearLayoutManager.HORIZONTAL,
                                    false);
                    category_rv.setLayoutManager(layoutManager);
                    category_rv.setAdapter(categoryAdapter);
                    Log.e("API","Successful");
                } else {
                    Log.e("API", "Empty or unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e("API", "Network error: " + t.getMessage());
            }
        });
    }



    private void setupCart() {
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
    }

    private void includeFragments() {
        includeBestSellerFood();
        includeNewFood();
    }

    private void includeNewFood() {
        // Giả sử bạn muốn truyền foodId là "123"
        String foodId = "123";

        // Tạo Fragment và truyền foodId qua Bundle
        Fragment_foodDisplay1 fragment = new Fragment_foodDisplay1();
        Bundle bundle = new Bundle();
        bundle.putString("food_id", foodId);  // Truyền foodId
        fragment.setArguments(bundle);  // Gắn Bundle vào Fragment

        // Thay thế fragment trong Activity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ms_bestSellerFood, fragment)  // Gắn vào một container trong HomeActivity
                .commit();
    }

    private void includeBestSellerFood() {
        // Giả sử bạn muốn truyền foodId là "123"
        String foodId = "123";

        // Tạo Fragment và truyền foodId qua Bundle
        Fragment_foodDisplay1 fragment = new Fragment_foodDisplay1();
        Bundle bundle = new Bundle();
        bundle.putString("food_id", foodId);  // Truyền foodId
        fragment.setArguments(bundle);  // Gắn Bundle vào Fragment

        // Thay thế fragment trong Activity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ms_newFood, fragment)  // Gắn vào một container trong HomeActivity
                .commit();
    }
}
