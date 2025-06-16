package com.example.app_foodstore.Adapter;


import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryListViewAdapter extends ArrayAdapter<CategoryModel> {

    private Context context;
    private List<CategoryModel> items;
    private OnCategoryClickListener onCategoryClickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryModel category);
    }

    public CategoryListViewAdapter(@NonNull Context context, List<CategoryModel> items, OnCategoryClickListener onCategoryClickListener) {
        super(context, 0, items);  // Cập nhật constructor để cung cấp danh sách items
        this.context = context;
        this.items = items;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CategoryModel getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_item_category_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CategoryModel model = items.get(position);

        // Set data for the views
        holder.tvName.setText(model.getName());
        holder.tvNumFoods.setText("Number of Foods: " + "10");  // Bạn có thể thay thế "10" bằng giá trị thực tế
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(new Date());
        holder.tvDateCreated.setText(formattedDate);
        Glide.with(context)
                .load(IMG_URL + model.getImage())
                .into(holder.imgIcon);
        // Set click listener trên convertView, không phải holder
        convertView.setOnClickListener(v -> {
            if (onCategoryClickListener != null) {
                onCategoryClickListener.onCategoryClick(items.get(position));
            }
        });


        return convertView;
    }

    public static class ViewHolder {
        ImageView imgIcon;
        TextView tvName;
        TextView tvNumFoods;
        TextView tvDateCreated;

        public ViewHolder(View view) {
            imgIcon = view.findViewById(R.id.item_row_category_imgCategory);
            tvName = view.findViewById(R.id.item_row_category_tv_categoryName);
            tvNumFoods = view.findViewById(R.id.item_row_category_tv_numFoods);
            tvDateCreated = view.findViewById(R.id.item_row_category_tv_dateCreated);
        }
    }
}
