package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.app_foodstore.Model.FoodImage;
import com.example.app_foodstore.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class ImageViewPager2Adapter extends RecyclerView.Adapter<ImageViewPager2Adapter.ImagesViewHolder> {

    private List<FoodImage> foodImages;
    private BottomSheetBehavior<CardView> bottomSheetBehavior;
    public ImageViewPager2Adapter(List<FoodImage> foodImages, BottomSheetBehavior<CardView> bottomSheetBehavior) {
        this.bottomSheetBehavior = bottomSheetBehavior;
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

        Glide.with(holder.itemView.getContext())
                .load(IMG_URL + foodImage.getImage_url())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Xử lý khi load thất bại
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Ảnh đã load xong
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                        return false;
                    }
                })
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return (foodImages != null) ? foodImages.size() : 0;
    }

    /*public void setFoodImages(List<FoodImage> foodImages) {
        this.foodImages = foodImages;
        notifyDataSetChanged();
    }*/

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }
}
