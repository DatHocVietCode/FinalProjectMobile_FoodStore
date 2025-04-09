package com.example.app_foodstore.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.List;

public class PopularFoodAdapter_rc extends RecyclerView.Adapter<PopularFoodAdapter_rc.ViewHolder> {
    public PopularFoodAdapter_rc(Context context, List<FoodModel> listFood) {
        this.context = context;
        this.listFood = listFood;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<FoodModel> getListFood() {
        return listFood;
    }

    public void setListFood(List<FoodModel> listFood) {
        this.listFood = listFood;
    }

    Context context;
    List<FoodModel> listFood;
    @NonNull
    @Override
    public PopularFoodAdapter_rc.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_display3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodAdapter_rc.ViewHolder holder, int position) {
        FoodModel food = listFood.get(position);
        holder.tv_name.setText(food.getName());
        holder.tv_cate.setText(food.getCategoryName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("idFood", food.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_cate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.fragment_food_display3_tv_foodName);
            tv_cate = itemView.findViewById(R.id.fragment_food_display3_tv_categoryName);
        }
    }
}
