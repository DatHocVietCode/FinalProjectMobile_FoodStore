package com.example.app_foodstore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.R;

public class Fragment_foodDisplay1 extends Fragment {
    private String foodId;  // Biến để lưu foodId
    private String foodName, categoryName;
    private Float foodPrice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_display1, container, false);

        // Lấy foodId từ Bundle
        if (getArguments() != null) {
            foodId = getArguments().getString("food_id");  // Nhận foodId
            foodName = getArguments().getString("food_name");
            foodPrice = getArguments().getFloat("food_price");
            categoryName = getArguments().getString("food_cateName");
            TextView tvFoodName = rootView.findViewById(R.id.fragment_food_display1_tv_foodName);
            tvFoodName.setText(foodName);
            TextView tvCategoryName = rootView.findViewById(R.id.fragment_food_display1_tv_categoryName);
            tvCategoryName.setText(categoryName);
            /*rootView.findViewById(R.id.fragment_food_display1_tv_foodPrice).setText(String.valueOf(foodPrice));
            rootView.findViewById(R.id.fragment_food_display1_img_food).setImageResource(R.drawable.food_sample);
            rootView.findViewById(R.id.fragment_food_display1_tv_categoryName).setText("Category Name");*/
        }

        // Thiết lập hành động khi người dùng click vào CardView
        CardView cardView = rootView.findViewById(R.id.fragment_food_display1_cardView);
        cardView.setOnClickListener(v -> {
            // Truyền foodId vào FoodDetailActivity
            Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
            intent.putExtra("food_id", foodId);  // Truyền foodId vào Intent
            startActivity(intent);
        });

        return rootView;
    }
}
