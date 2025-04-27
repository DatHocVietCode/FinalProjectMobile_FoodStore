package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.List;

public class FoodTabLayoutAdapter extends RecyclerView.Adapter<FoodTabLayoutAdapter.FoodTabLayoutViewHolder> {
    Context context;
    List<FoodModel> list;

    public FoodTabLayoutAdapter(Context context, List<FoodModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FoodTabLayoutAdapter.FoodTabLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_display2, parent, false);
        return new FoodTabLayoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTabLayoutAdapter.FoodTabLayoutViewHolder holder, int position) {
        FoodModel foodModel = list.get(position);
        holder.tv_foodName.setText(foodModel.getName());
        //holder.img_food.setImageResource(R.drawable.food_sample);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FoodTabLayoutViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foodName;
        ImageView img_food;
        public FoodTabLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foodName = itemView.findViewById(R.id.fragment_food_display2_tv_foodName);
            img_food = itemView.findViewById(R.id.fragment_food_display2_img_food);
        }
    }
}
