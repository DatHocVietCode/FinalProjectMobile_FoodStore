package com.example.app_foodstore.Adapter;


import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.List;

public class SuggestedFoodAdapter extends RecyclerView.Adapter<SuggestedFoodAdapter.SuggestedFoodViewHolder> {
    Context context;
    List<FoodModel> listFood;

    public SuggestedFoodAdapter(Context context, List<FoodModel> listFood) {
        this.context = context;
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public SuggestedFoodAdapter.SuggestedFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_food_display2, parent, false);
        return new SuggestedFoodViewHolder(view);
    }

    // Chưa có dữ liệu, có rồi làm
    @Override
    public void onBindViewHolder(@NonNull SuggestedFoodAdapter.SuggestedFoodViewHolder holder, int position) {
        FoodModel food = listFood.get(position);
        Glide.with(context).load(IMG_URL + food.getThumbnail()).into(holder.imgFood);
        holder.tvFoodName.setText(food.getName());
        holder.tvRating.setText(String.valueOf(food.getAverage_rating()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("foodModel", food);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class SuggestedFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName, tvRating;
        public SuggestedFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.fragment_food_display2_img_food);
            tvFoodName = itemView.findViewById(R.id.fragment_food_display2_tv_foodName);
            tvRating = itemView.findViewById(R.id.fragment_food_display2_tv_rating);
        }
    }
}
