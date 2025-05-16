package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private List<MyOrderPendingDTO> orderList = new ArrayList<>();

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order_item_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        MyOrderPendingDTO order = orderList.get(position);

        String createdDate = order.getCreated();
        String formattedDate = formatDateString(createdDate);
        holder.tv_orderDate.setText(formattedDate);

        // Set status visibility
        String status = order.getStatus();
        if (status != null) {
            if ("COMPLETED".equalsIgnoreCase(status)) {
                holder.tv_orderCompleted.setVisibility(View.VISIBLE);
                holder.tv_orderCanceled.setVisibility(View.GONE);
            } else if ("CANCELLED".equalsIgnoreCase(status)) {
                holder.tv_orderCompleted.setVisibility(View.GONE);
                holder.tv_orderCanceled.setVisibility(View.VISIBLE);
            } else {
                holder.tv_orderCompleted.setVisibility(View.GONE);
                holder.tv_orderCanceled.setVisibility(View.GONE);
            }
        } else {
            holder.tv_orderCompleted.setVisibility(View.GONE);
            holder.tv_orderCanceled.setVisibility(View.GONE);
        }

        // Set other fields
        holder.tv_orderId.setText("#" + (order.getIdOrder() != null ? order.getIdOrder() : ""));
        holder.tv_foodName.setText(order.getProducts().get(0).getFoodName());
        holder.tv_totalprice.setText(String.valueOf(order.getTotalPrice()));
        holder.tv_category.setText((order.getProducts().get(0).getCategory()));
        holder.tv_items.setText(String.valueOf(order.getProducts().size()));
        Glide.with(holder.img_food.getContext()).load(IMG_URL + order.getProducts().get(0).getThumbnail()).into(holder.img_food);
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public void setData(List<MyOrderPendingDTO> data) {
        this.orderList = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    private String formatDateString(String isoDate) {
        if (isoDate == null) return "";
        try {
            // ISO 8601 format example: "2024-05-15T13:45:00"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = inputFormat.parse(isoDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return isoDate; // Nếu lỗi thì hiển thị nguyên bản
        }
    }

    static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_orderDate, tv_orderCompleted, tv_orderCanceled, tv_orderId,tv_foodName,tv_category,tv_items,tv_totalprice;
        ImageView img_food;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderDate = itemView.findViewById(R.id.fragment_order_item_history_tv_dateOrdered);
            tv_orderCompleted = itemView.findViewById(R.id.fragment_order_item_history_tv_status_completed);
            tv_orderCanceled = itemView.findViewById(R.id.fragment_order_item_history_tv_status_canceled);
            tv_orderId = itemView.findViewById(R.id.fragment_order_item_history_tv_foodID);
            tv_category = itemView.findViewById(R.id.fragment_order_item_history_tv_categoryName);
            tv_foodName = itemView.findViewById(R.id.fragment_order_item_history_tv_foodName);
            tv_items = itemView.findViewById(R.id.fragment_order_item_history_tv_itemCount);
            tv_totalprice = itemView.findViewById(R.id.fragment_order_item_history_tv_price);
            img_food = itemView.findViewById(R.id.fragment_order_item_history_img_food);
        }
    }
}
