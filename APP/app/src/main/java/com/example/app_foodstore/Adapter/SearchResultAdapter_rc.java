package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.List;

public class SearchResultAdapter_rc extends RecyclerView.Adapter<SearchResultAdapter_rc.ViewHolder> {
    Context context;
    List<FoodModel> foods;

    public SearchResultAdapter_rc(Context context, List<FoodModel> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public SearchResultAdapter_rc.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fragment_food_display4, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter_rc.ViewHolder holder, int position) {
        FoodModel food = foods.get(position);
        holder.BindingData(food);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        ImageButton btn_add;
        TextView tv_foodName, tv_categoryName, tv_price;
        FrameLayout layout_food;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.fragment_food_display4_imgFood);
            btn_add = itemView.findViewById(R.id.fragment_food_display4_btn_add);
            tv_foodName = itemView.findViewById(R.id.fragment_food_display4_tv_foodName);
            tv_categoryName = itemView.findViewById(R.id.fragment_food_display4_tv_categoryName);
            tv_price = itemView.findViewById(R.id.fragment_food_display4_tv_price);
            layout_food = itemView.findViewById(R.id.fragment_food_display4);
        }
        public void BindingData(FoodModel food)
        {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Add to cart: " + food.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            layout_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Click on food: " + food.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
