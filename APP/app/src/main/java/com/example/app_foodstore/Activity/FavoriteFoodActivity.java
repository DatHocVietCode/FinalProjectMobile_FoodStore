package com.example.app_foodstore.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.FavoriteFoodAdapter;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFoodActivity extends AppCompatActivity {
    RecyclerView rc_food;
    FavoriteFoodAdapter adapter;
    List<FoodModel> favFoods;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_food);
        AnhXa();
    }

    private void AnhXa() {
        setupRc();
    }

    private void setupRc() {
        getFoods();
        rc_food = findViewById(R.id.favFoodScreen_rc_food);
        adapter = new FavoriteFoodAdapter(this, favFoods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_food.setLayoutManager(linearLayoutManager);
        rc_food.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getFoods() {
        favFoods = new ArrayList<>();
        favFoods.add(new FoodModel("Cate1", "Food Name", 1L));
        favFoods.add(new FoodModel("Cate1", "Food Name", 1L));
        favFoods.add(new FoodModel("Cate1", "Food Name", 1L));
        favFoods.add(new FoodModel("Cate1", "Food Name", 1L));
    }
}
