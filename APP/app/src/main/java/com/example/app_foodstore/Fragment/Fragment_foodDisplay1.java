package com.example.app_foodstore.Fragment;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

public class Fragment_foodDisplay1 extends Fragment {
    private FoodModel foodModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_display1, container, false);

        // Lấy FoodModel từ Bundle
        if (getArguments() != null) {
            foodModel = (FoodModel) getArguments().getSerializable("food_model");

            if (foodModel != null) {
                // Tìm layout con đã include
                View includeLayout = rootView.findViewById(R.id.include_food_rating);

                // Tìm từng TextView trong layout con
                TextView tvRating = includeLayout.findViewById(R.id.fragment_food_rating_tv_rating);
                TextView tvDelivery = includeLayout.findViewById(R.id.fragment_food_rating_tv_delivery);
                TextView tvTime = includeLayout.findViewById(R.id.fragment_food_rating_tv_time);

                // Thiết lập dữ liệu
                TextView tvFoodName = rootView.findViewById(R.id.fragment_food_display1_tv_foodName);
                tvFoodName.setText(foodModel.getName());

                TextView tvCategoryName = rootView.findViewById(R.id.fragment_food_display1_tv_categoryName);
                tvCategoryName.setText(foodModel.getCategory_name());

                tvRating.setText(String.valueOf(foodModel.getAverage_rating()));
                // Nếu có delivery hoặc time thì set thêm

//                TextView tvPrice = rootView.findViewById(R.id.fragment_food_display1_tv_foodPrice);
//                tvPrice.setText(String.format("%.0fđ", foodModel.getPrice()));

                ImageView imageView = rootView.findViewById(R.id.fragment_food_display1_img_food);
                String imageUrl = IMG_URL + foodModel.getThumbnail();
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.food_sample)
                        .error(R.drawable.food_sample)
                        .into(imageView);
            }
        }

    // Thiết lập click để mở FoodDetailActivity
        CardView cardView = rootView.findViewById(R.id.fragment_food_display1_cardView);
        cardView.setOnClickListener(v -> {
            if (foodModel != null) {
                Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
                intent.putExtra("foodModel", foodModel);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
