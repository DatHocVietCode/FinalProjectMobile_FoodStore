package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class OrderOnGoingAdapter extends RecyclerView.Adapter<OrderOnGoingAdapter.OrderViewHolder> {

    private List<MyOrderPendingDTO> orderList = new ArrayList<>();
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onTrackClicked(MyOrderPendingDTO order);
        void onCancelClicked(MyOrderPendingDTO order);
    }

    public void setOnOrderClickListener(OnOrderClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order_item_ongoing, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        MyOrderPendingDTO order = orderList.get(position);

        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            var product = order.getProducts().get(0);
            holder.tvCategoryName.setText(product.getCategory() != null ? product.getCategory() : "Unknown Category");
            holder.tvFoodName.setText(product.getFoodName() != null ? product.getFoodName() : "Unknown Food");

            String thumbnail = product.getThumbnail();
            if (thumbnail != null && !thumbnail.isEmpty()) {
                Glide.with(holder.imgFood.getContext())
                        .load(IMG_URL + thumbnail)
                        .into(holder.imgFood);
            } else {
                holder.imgFood.setImageResource(R.drawable.food_sample);
            }

            holder.tvItemCount.setText(String.valueOf(order.getProducts().size()));
        } else {
            holder.tvCategoryName.setText("No Category");
            holder.tvFoodName.setText("No Product");
            holder.tvItemCount.setText("0");
            holder.imgFood.setImageResource(R.drawable.food_sample);
        }

        holder.tvToio.setText(order.getStatus() != null ? order.getStatus() : "Unknown");
        holder.tvFoodId.setText(order.getIdOrder() != null ? "#" + order.getIdOrder() : "#N/A");
        holder.tvPrice.setText(order.getTotalPrice() != null ? String.valueOf(order.getTotalPrice()) : "0");

        // Handle button clicks
        holder.btnTrack.setOnClickListener(v -> {
            if (listener != null) listener.onTrackClicked(order);
        });

        holder.btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancelClicked(order);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setData(List<MyOrderPendingDTO> data) {
        this.orderList = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName, tvToio, tvFoodName, tvFoodId, tvPrice, tvItemCount;
        ImageView imgFood;
        Button btnTrack, btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_categoryName);
            tvToio = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_toio);
            tvFoodName = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_foodName);
            tvFoodId = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_foodID);
            tvPrice = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_price);
            tvItemCount = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_itemCount);
            imgFood = itemView.findViewById(R.id.fragment_order_item_ongoing_imgFood);
            btnTrack = itemView.findViewById(R.id.fragment_order_item_ongoing_btn_track);
            btnCancel = itemView.findViewById(R.id.fragment_order_item_ongoing_btn_cancel);
        }
    }
}
