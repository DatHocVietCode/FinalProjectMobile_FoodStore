package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.R;

import java.util.List;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ItemCartAdapterViewHolder> {
    Context context;
    List<CartModel> list;

    public ItemCartAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

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
        CartModel cartItem = list.get(position);

        // Hiển thị số lượng hiện tại
        holder.tv_count.setText(String.valueOf(cartItem.getQuantity()));

        // ================================
        // 🎯 Xử lý sự kiện nút Delete
        // ================================
        holder.btn_delete.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        holder.btn_delete.setOnClickListener(view -> {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
        });

        // ================================
        // ➖ Xử lý sự kiện nút Minus (-)
        // ================================
        holder.btn_minus.setOnClickListener(view -> {
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity > 1) {
                currentQuantity--;
                cartItem.setQuantity(currentQuantity);
                holder.tv_count.setText(String.valueOf(currentQuantity));
            } else {
                Toast.makeText(view.getContext(), "Minimum quantity reached", Toast.LENGTH_SHORT).show();
            }
        });

        // ================================
        // ➕ Xử lý sự kiện nút Add (+)
        // ================================
        holder.btn_add.setOnClickListener(view -> {
            int currentQuantity = cartItem.getQuantity();
            currentQuantity++;
            cartItem.setQuantity(currentQuantity);
            holder.tv_count.setText(String.valueOf(currentQuantity));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemCartAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_delete, btn_minus, btn_add;
        TextView tv_count;
        public ItemCartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_delete = itemView.findViewById(R.id.cart_btn_deleteItem);
            btn_minus = itemView.findViewById(R.id.cart_btn_minus);
            btn_add = itemView.findViewById(R.id.cart_tv_add);
            tv_count = itemView.findViewById(R.id.cart_tv_count);
        }
    }
}
