package com.example.app_foodstore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.List;

public class FavoriteFoodAdapter extends RecyclerView.Adapter<FavoriteFoodAdapter.FavFoodViewHolder> {
    Context context;
    List<FoodModel> favFoods;

    public FavoriteFoodAdapter(Context context, List<FoodModel> favFoods) {
        this.context = context;
        this.favFoods = favFoods;
    }

    @NonNull
    @Override
    public FavoriteFoodAdapter.FavFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_food_display2, parent, false);
        return new FavFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFoodAdapter.FavFoodViewHolder holder, int position) {
        FoodModel food = favFoods.get(position);
        // API
        /*holder.img_food.setImageResource(food.getImg_food());
        holder.tv_name.setText(food.getName());
        holder.tv_rating.setText(food.getRating());*/
        holder.btn_favorite.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                context.startActivity(intent);
            }
        });
        holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Dialog đơn giản
                AlertDialog dialog = new android.app.AlertDialog.Builder(context)
                        .setTitle("Remove Favorite")
                        .setMessage("Are you sure you want to remove this item from favorites?")
                        .setPositiveButton("Yes", (dialog1, which) -> {
                            // Xử lý khi người dùng chọn "Yes"
                            favFoods.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyItemRangeChanged(holder.getAdapterPosition(), favFoods.size());
                        })
                        .setNegativeButton("No", (dialog12, which) -> {
                            // Đóng dialog nếu người dùng chọn "No"
                            dialog12.dismiss();
                        })
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF7622"));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF7622"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return favFoods.size();
    }

    public class FavFoodViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_favorite;
        ImageView img_food;
        TextView tv_name;
        TextView tv_rating;

        public FavFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_favorite = itemView.findViewById(R.id.fragment_food_display2_btn_favorite);
            img_food = itemView.findViewById(R.id.fragment_food_display2_img_food);
            tv_name = itemView.findViewById(R.id.fragment_food_display2_tv_foodName);
            tv_rating = itemView.findViewById(R.id.fragment_food_display2_tv_rating);
        }
    }
}
