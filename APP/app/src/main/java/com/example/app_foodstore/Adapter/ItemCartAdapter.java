package com.example.app_foodstore.Adapter;



import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;  // nhớ thêm dependency Glide trong build.gradle nếu dùng
import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ItemCartAdapterViewHolder> {

    public interface OnCartItemActionListener {
        void onQuantityChanged(CartModel cartItem, int newQuantity);
        void onDeleteItem(CartModel cartItem);
    }
    private OnItemChangeListener onItemChangeListener;

    // Định nghĩa Interface ngay trong Adapter
    public interface OnItemChangeListener {
        void onItemChanged();
    }
    private Context context;
    private List<CartModel> list;
    private boolean isEditing = false;
    private OnCartItemActionListener actionListener;

    // Constructor mới dùng listener mở rộng
    public ItemCartAdapter(Context context, List<CartModel> list, OnCartItemActionListener listener
    , OnItemChangeListener listener1) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.actionListener = listener;
        this.onItemChangeListener = listener1;
    }

    public void setEditMode(boolean isEditing) {
        this.isEditing = isEditing;
        notifyDataSetChanged();
    }

    public void updateCartList(List<CartModel> newList) {
        this.list = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemCartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ItemCartAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartAdapterViewHolder holder, int position) {
        CartModel cartItem = list.get(position);

        holder.tv_name.setText(cartItem.getName());
        holder.tv_price.setText(String.format("%,.0f đ", cartItem.getPrice()));
        holder.tv_count.setText(String.valueOf(cartItem.getQuantity()));

        if (cartItem.getThumbnail() != null && !cartItem.getThumbnail().isEmpty()) {
            Glide.with(context)
                    .load(IMG_URL + cartItem.getThumbnail())
                    .placeholder(R.drawable.food_sample)
                    .into(holder.img_food);
        } else {
            holder.img_food.setImageResource(R.drawable.food_sample);
        }

        holder.btn_delete.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        holder.btn_delete.setOnClickListener(view -> {
            if (actionListener != null) {
                actionListener.onDeleteItem(cartItem); // Gọi callback xử lý API
            }
            if (onItemChangeListener != null) {
                onItemChangeListener.onItemChanged();
            }
        });

        holder.btn_minus.setOnClickListener(view -> {
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity > 1) {
                int newQuantity = currentQuantity - 1;
                cartItem.setQuantity(newQuantity);
                holder.tv_count.setText(String.valueOf(newQuantity));

                if (actionListener != null) {
                    actionListener.onQuantityChanged(cartItem, newQuantity);
                }
            } else {
                Toast.makeText(view.getContext(), "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
            }
            if (onItemChangeListener != null) {
                onItemChangeListener.onItemChanged();
            }
        });

        holder.btn_add.setOnClickListener(view -> {
            int newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
            holder.tv_count.setText(String.valueOf(newQuantity));

            if (actionListener != null) {
                actionListener.onQuantityChanged(cartItem, newQuantity);
            }
            if (onItemChangeListener != null) {
                onItemChangeListener.onItemChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemCartAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_delete, btn_minus, btn_add;
        TextView tv_count, tv_name, tv_price;
        ImageView img_food;

        public ItemCartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_delete = itemView.findViewById(R.id.cart_btn_deleteItem);
            btn_minus = itemView.findViewById(R.id.cart_btn_minus);
            btn_add = itemView.findViewById(R.id.cart_tv_add);
            tv_count = itemView.findViewById(R.id.cart_tv_count);
            tv_name = itemView.findViewById(R.id.cart_tv_foodName);
            tv_price = itemView.findViewById(R.id.cart_tv_price);
            img_food = itemView.findViewById(R.id.item_cart_img_food);
        }
    }
}
