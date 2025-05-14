package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;

import java.util.List;

public class FoodTabLayoutAdapter extends RecyclerView.Adapter<FoodTabLayoutAdapter.FoodTabLayoutViewHolder> {
    Context context;
    List<FoodModel> list;
    int tabNum = 0;
    public FoodTabLayoutAdapter(Context context, List<FoodModel> list, int tabNum) {
        this.context = context;
        this.list = list;
        this.tabNum = tabNum;
    }

    @NonNull
    @Override
    public FoodTabLayoutAdapter.FoodTabLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_display_tablayout, parent, false);
        return new FoodTabLayoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTabLayoutAdapter.FoodTabLayoutViewHolder holder, int position) {
        FoodModel foodModel = list.get(position);
        holder.tv_foodName.setText(foodModel.getName());
        holder.tv_price.setText("$" + foodModel.getPrice());
        holder.tv_categoryName.setText(foodModel.getCategory_name());

        holder.tv_rating.setText(foodModel.getAverage_rating().toString());
        holder.tv_comments.setText(foodModel.getCount_comment().toString() + " comments");
        Glide.with(context)
                .load(IMG_URL + foodModel.getThumbnail())
                .into(holder.img_food);
        switch (tabNum)
        {
            case 0:
                holder.layout_bestSeller.setVisibility(View.GONE);
                holder.layout_new.setVisibility(View.GONE);
                break;
            case 1:
                holder.layout_bestSeller.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.layout_new.setVisibility(View.VISIBLE);
                break;
            default:
                holder.layout_bestSeller.setVisibility(View.GONE);
                holder.layout_new.setVisibility(View.GONE);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("foodModel", foodModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FoodTabLayoutViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foodName, tv_categoryName, tv_price, tv_comments, tv_rating;
        ImageView img_food;
        ConstraintLayout layout_bestSeller, layout_new;
        public FoodTabLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foodName = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_foodName);
            img_food = itemView.findViewById(R.id.fragment_food_display_tablayout_img_food);
            tv_categoryName = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_categoryName);
            tv_price = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_price);
            tv_comments = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_comments);
            layout_bestSeller = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_best_seller);
            layout_new = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_new);
            tv_rating = itemView.findViewById(R.id.fragment_food_display_tablayout_tv_rating);
        }
    }
}
