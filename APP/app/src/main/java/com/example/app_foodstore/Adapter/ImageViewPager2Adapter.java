package com.example.app_foodstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.ImageModel;
import com.example.app_foodstore.R;

import java.util.List;

public class ImageViewPager2Adapter extends RecyclerView.Adapter<ImageViewPager2Adapter.ImagesViewHolder> {
    private List<ImageModel> imagesList;

    public ImageViewPager2Adapter(List<ImageModel> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImageViewPager2Adapter.ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewPager2Adapter.ImagesViewHolder holder, int position) {
        ImageModel images = imagesList.get(position);
        if (images == null)
        {
            return;
        }
        holder.imageView.setImageResource(images.getImageId());
    }

    @Override
    public int getItemCount() {
        if (imagesList != null)
        {
            return imagesList.size();
        }
        return 0;
    }

    public List<ImageModel> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<ImageModel> imagesList) {
        this.imagesList = imagesList;
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }
}
