package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.R;

public class OrderOnGoingAdapter extends RecyclerView.Adapter<OrderOnGoingAdapter.OrderOnGoingViewHolder> {
    Context context;
    // Tính tới cái model sau
    public OrderOnGoingAdapter(Context context) {
        this.context = context;
    }

    //List<FoodModel> list;
    @NonNull
    @Override
    public OrderOnGoingAdapter.OrderOnGoingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_item_ongoing, parent, false);
        return new OrderOnGoingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderOnGoingAdapter.OrderOnGoingViewHolder holder, int position) {
        // Vẽ underline cho text
        holder.tv_Foodid.setText("#123");
        holder.tv_Foodid.setPaintFlags(holder.tv_Foodid.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class OrderOnGoingViewHolder extends RecyclerView.ViewHolder {
        // Tính tới cái model sau
        TextView tv_Foodid;
        public OrderOnGoingViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Foodid = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_foodID);
        }
    }
}
