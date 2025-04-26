package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.R;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<CategoryModel> {
    Context context;
    List<CategoryModel> categoryList;
    private CategorySpinnerAdapter.OnCategorySelectedListener listener;
    public interface OnCategorySelectedListener {
        void OnCategorySelectedListener(CategoryModel categoryModel, int position);
    }
    public CategorySpinnerAdapter(@NonNull Context context, List<CategoryModel> categoryList, CategorySpinnerAdapter.OnCategorySelectedListener listener) {
        super(context, 0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout cho item trong spinner
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_voucher_item, parent, false);
        }

        CategoryModel categoryModel = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);
        textView.setText(categoryModel.getName());

        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Inflate layout cho dropdown
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_voucher_item, parent, false);
        }

        CategoryModel categoryModel = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);
        textView.setText(categoryModel.getName());
        // Gắn sự kiện click
        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.OnCategorySelectedListener(categoryModel, position);
            }
        });
        return convertView;
    }

}
