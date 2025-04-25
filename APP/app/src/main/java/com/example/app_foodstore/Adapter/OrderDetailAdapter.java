package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    Context context;
    List<OrderDetailModel> orderDetailModelList;

    public OrderDetailAdapter(Context context, List<OrderDetailModel> orderDetailModelList) {
        this.context = context;
        this.orderDetailModelList = orderDetailModelList;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_order_detail, null);
        return new OrderDetailAdapter.OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        holder.tvFoodName.setText(orderDetailModelList.get(position).getFoodName());
        holder.tvNumber.setText("x" + orderDetailModelList.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return orderDetailModelList.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvNumber;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.item_order_detail_tv_foodName);
            tvNumber = itemView.findViewById(R.id.item_order_detail_tv_number);
        }
    }
}
