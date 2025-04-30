package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Adapter.CategoryListViewAdapter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.CateViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    ListView lv_categories;
    CategoryListViewAdapter adapter;
    CateViewModel cateViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        AnhXa();
    }

    private void AnhXa() {
        iniViewModel();
        setupListView();
    }

    private void iniViewModel() {
        cateViewModel = new ViewModelProvider(this).get(CateViewModel.class);
    }

    private void setupListView() {
        lv_categories = findViewById(R.id.listCategory_lv_categories);
        cateViewModel.getAllCate().observe(this, categories -> {
            if (categories != null) {
                adapter = new CategoryListViewAdapter(this, categories, new CategoryListViewAdapter.OnCategoryClickListener() {
                    @Override
                    public void onCategoryClick(CategoryModel category) {
                        Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
                        intent.putExtra("categoryId", category.getId());
                        startActivity(intent);
                        Log.d("logdd", "onCategoryClick: " + category.getId());
                    }
                });
                lv_categories.setAdapter(adapter);
            }
        });
    }
}
