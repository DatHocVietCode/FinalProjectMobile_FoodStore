package com.example.app_foodstore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;

import java.util.List;

public class OrderOnGoingAdapter extends RecyclerView.Adapter<OrderOnGoingAdapter.OrderOnGoingViewHolder> {
    Context context;
    // Tính tới cái model sau
    List<OrderModel> listOrderOngoing;
    public OrderOnGoingAdapter(Context context, List<OrderModel> listOrderOngoing) {
        this.context = context;
        this.listOrderOngoing = listOrderOngoing;
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
        holder.btn_cancel.setOnClickListener(v -> {
            // Tạo AlertDialog
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Xác nhận hủy")
                    .setMessage("Bạn có chắc chắn muốn hủy đơn hàng này không?")
                    .setPositiveButton("Yes", (dialog1, which) -> {
                        listOrderOngoing.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("No", (dialog12, which) -> {
                        dialog12.dismiss();
                    })
                    .show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF7622"));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF7622"));

        });

    }

    @Override
    public int getItemCount() {
        return listOrderOngoing.size();
    }


    public class OrderOnGoingViewHolder extends RecyclerView.ViewHolder {
        // Tính tới cái model sau
        TextView tv_Foodid;
        Button btn_cancel, btn_track;
        public OrderOnGoingViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Foodid = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_foodID);
            btn_cancel = itemView.findViewById(R.id.fragment_order_item_ongoing_btn_cancel);
            btn_track = itemView.findViewById(R.id.fragment_order_item_ongoing_btn_track);
        }
    }
}
