package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.Adapter.CategoryListViewAdapter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    ListView lv_categories;
    CategoryListViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        AnhXa();
    }

    private void AnhXa() {
        setupListView();
    }

    private void setupListView() {
        lv_categories = findViewById(R.id.listCategory_lv_categories);
        List<CategoryModel> categories = new ArrayList<>();
        categories.add(new CategoryModel(1,"Category 1", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(2,"Category 2", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(3,"Category 3", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(4,"Category 4", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(5,"Category 5", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(6,"Category 6", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(7,"Category 7", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(8,"Category 8", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(9,"Category 9", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(10,"Category 10", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(1,"Category 1", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(2,"Category 2", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(3,"Category 3", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(4,"Category 4", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(5,"Category 5", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(6,"Category 6", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(7,"Category 7", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(8,"Category 8", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(9,"Category 9", "https://picsum.photos/200/300"));
        categories.add(new CategoryModel(10,"Category 10", "https://picsum.photos/200/300"));

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
}
