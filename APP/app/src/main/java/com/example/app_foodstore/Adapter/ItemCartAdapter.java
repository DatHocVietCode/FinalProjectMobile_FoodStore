package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.R;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ItemCartAdapterViewHolder> {
    Context context;
    // File: ItemCartAdapter.java (bên trong class hoặc riêng cũng được)
    public interface OnEditModeChangeListener {
        void onEditModeChanged(boolean isEditing);
    }
    public void setEditMode(boolean isEditing) {
        this.isEditing = isEditing;
        notifyDataSetChanged(); // Cập nhật giao diện
    }
    private boolean isEditing = false;
    public ItemCartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemCartAdapter.ItemCartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ItemCartAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartAdapter.ItemCartAdapterViewHolder holder, int position) {
        holder.btn_delete.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemCartAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_delete;
        public ItemCartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_delete = itemView.findViewById(R.id.cart_btn_deleteItem);
        }
    }
}
