package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    Context context;
    List<OrderModel> listOrder;

    public OrderHistoryAdapter(Context context, List<OrderModel> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_item_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        OrderModel orderModel = listOrder.get(position);
        holder.tv_orderDate.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(orderModel.getOrderDate()));
        if (orderModel.isCompleted())
        {
            holder.tv_orderCompleted.setVisibility(View.VISIBLE);
            holder.tv_orderCanceled.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_orderCompleted.setVisibility(View.GONE);
            holder.tv_orderCanceled.setVisibility(View.VISIBLE);
        }
        holder.tv_foodId.setText("#123");
        holder.tv_foodId.setPaintFlags(holder.tv_foodId.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_orderDate, tv_orderCompleted, tv_orderCanceled, tv_foodId;
        Button btn_rate, btn_reOrder;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderDate = itemView.findViewById(R.id.fragment_order_item_history_tv_dateOrdered);
            tv_orderCompleted = itemView.findViewById(R.id.fragment_order_item_history_tv_status_completed);
            tv_orderCanceled = itemView.findViewById(R.id.fragment_order_item_history_tv_status_canceled);
            tv_foodId = itemView.findViewById(R.id.fragment_order_item_history_tv_foodID);
            btn_rate = itemView.findViewById(R.id.fragment_order_item_history_btn_rate);
            btn_reOrder = itemView.findViewById(R.id.fragment_order_item_history_btn_reOrder);
        }
    }
}
