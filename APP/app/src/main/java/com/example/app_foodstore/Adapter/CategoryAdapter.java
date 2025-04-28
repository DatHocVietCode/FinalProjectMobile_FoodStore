package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.API.APIClient;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    public CategoryAdapter() {
    }

    private Context context;

    public CategoryAdapter(Context context, List<CategoryModel> listCate) {
        this.context = context;
        this.listCate = listCate;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public List<CategoryModel> getListCate() {
        return listCate;
    }

    public void setListCate(List<CategoryModel> listCate) {
        this.listCate = listCate;
    }

    private List<CategoryModel> listCate;
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = listCate.get(position);
        String imageUrl = APIClient.IMAGE_BASE_URL + categoryModel.getImage();
        Glide.with(context)
                .load(imageUrl)
                .into(holder.category_image);
        holder.tv_name.setText(categoryModel.getName());
    }

    @Override
    public int getItemCount() {
        return listCate.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView category_image;
        TextView tv_name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.fragment_category_item_categoryImage);
            tv_name = itemView.findViewById(R.id.fragment_category_item_categoryName);
        }
    }
}
