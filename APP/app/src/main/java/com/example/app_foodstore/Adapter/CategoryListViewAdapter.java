package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryListViewAdapter extends ArrayAdapter<CategoryModel> {
    private Context context;
    private List<CategoryModel> items;
    public CategoryListViewAdapter(@NonNull Context context, List<CategoryModel> items) {
        super(context, 0);
        this.context = context;
        this.items = items;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = convertView;

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.item_category_row, parent, false);
        }

        ImageView imgIcon = rowView.findViewById(R.id.item_row_category_imgCategory);
        TextView tvName = rowView.findViewById(R.id.item_row_category_tv_categoryName);
        TextView tvNumFoods = rowView.findViewById(R.id.item_row_category_tv_numFoods);
        TextView tvDateCreated = rowView.findViewById(R.id.item_row_category_tv_dateCreated);

        CategoryModel model = items.get(position);
        tvName.setText(model.getName());
        tvNumFoods.setText("Number of Foods: " + "10");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(new Date());
        tvDateCreated.setText(formattedDate);

        return rowView;
    }
}
