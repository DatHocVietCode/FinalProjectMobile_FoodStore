package com.example.app_foodstore.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.CategorySpinnerAdapter;
import com.example.app_foodstore.Adapter.SearchResultAdapter_rc;
import com.example.app_foodstore.Fragment.Fragment_btn_cart;
import com.example.app_foodstore.Fragment.Fragment_btn_filter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    RecyclerView ss_result_rc_searchResult;
    List<FoodModel> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Lấy keyword để sau này gọi API hoặc lọc danh sách kết quả
        String keyword = getIntent().getStringExtra("keyword");
        if (keyword != null) {
            Log.d("SearchResult", "Từ khóa: " + keyword);
        }

        AnhXa(); // khởi tạo RecyclerView
    }


    private void AnhXa() {
        setupRcSearchResult();
        setupBtnFilter();
    }

    private void setupBtnFilter() {
        /*ImageButton filterBtn = findViewById(R.id.ss_result_header_btn_filter);
        filterBtn.setOnClickListener(v -> {

        });*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment_btn_filter btn_filter = new Fragment_btn_filter();
        transaction.replace(R.id.ss_result_fragmentContainer_btnFilter, btn_filter);
        transaction.commit();
    }

    private void setupRcSearchResult() {
        ss_result_rc_searchResult = findViewById(R.id.ss_result_rc_searchResult);
        foods = new ArrayList<>();
        foods.add(new FoodModel("Cate 1", "Món ăn 1",1L));
        foods.add(new FoodModel("Cate 2", "Món ăn 2",1L));
        foods.add(new FoodModel("Cate 3", "Món ăn 3",1L));
        foods.add(new FoodModel("Cate 4", "Món ăn 4",1L));
        foods.add(new FoodModel("Cate 5", "Món ăn 5",1L));
        foods.add(new FoodModel("Cate 6", "Món ăn 6",1L));
        foods.add(new FoodModel("Cate 7", "Món ăn 7",1L));
        foods.add(new FoodModel("Cate 8", "Món ăn 8",1L));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        ss_result_rc_searchResult.setLayoutManager(layoutManager);
        ss_result_rc_searchResult.setAdapter(new SearchResultAdapter_rc(this, foods));
    }
}
