package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.FoodImage;
import com.example.app_foodstore.R;

import java.util.List;

public class ImageViewPager2Adapter extends RecyclerView.Adapter<ImageViewPager2Adapter.ImagesViewHolder> {

    private List<FoodImage> foodImages;

    public ImageViewPager2Adapter(List<FoodImage> foodImages) {
        this.foodImages = foodImages;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        FoodImage foodImage = foodImages.get(position);
        if (foodImage == null) return;

        // Load image từ URL bằng Glide
        Glide.with(holder.itemView.getContext())
                .load(IMG_URL +  foodImage.getImage_url())
                .placeholder(R.drawable.coffee) // ảnh hiển thị khi đang load
                .error(R.drawable.coffee)       // ảnh hiển thị nếu load thất bại
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (foodImages != null) ? foodImages.size() : 0;
    }

    public void setFoodImages(List<FoodImage> foodImages) {
        this.foodImages = foodImages;
        notifyDataSetChanged();
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }
}
