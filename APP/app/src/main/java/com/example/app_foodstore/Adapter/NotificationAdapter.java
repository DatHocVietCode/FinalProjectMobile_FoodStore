package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Activity.BreakDownOrderActivity;
import com.example.app_foodstore.Activity.TrackOrderActivity;
import com.example.app_foodstore.Model.NotificationModel;
import com.example.app_foodstore.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    Context context;
    List<NotificationModel> list;

    public NotificationAdapter(Context context, List<NotificationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationModel notify = list.get(position);
        // Này là API
        holder.item_row_notification_tvOrderId.setText(list.get(position).getOrderId());
        holder.item_row_notification_tvDecs.setText(list.get(position).getDecs());
        holder.item_row_notification_gapTime.setText(notify.getGapTime());
        holder.item_row_notification_img_food.setImageResource(notify.getImage());
        switch (notify.getStatusId()){
            case 0:
                holder.item_row_notification_tvStatus.setText(notify.getStatus());
                holder.item_row_notification_tvStatus.setTextColor(context.getResources().getColor(R.color.onGoing));
                break;
            case 1:
                holder.item_row_notification_tvStatus.setText(notify.getStatus());
                holder.item_row_notification_tvStatus.setTextColor(context.getResources().getColor(R.color.completed));
                break;
            case 2:
                holder.item_row_notification_tvStatus.setText(notify.getStatus());
                holder.item_row_notification_tvStatus.setTextColor(context.getResources().getColor(R.color.canceled));
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (notify.getStatusId())
                {
                    case 0:
                        Intent intent = new Intent(context, TrackOrderActivity.class);
                        intent.putExtra("orderId",notify.getOrderId());
                        intent.putExtra("statusId",notify.getStatusId());
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(context, BreakDownOrderActivity.class);
                        intent1.putExtra("orderId",notify.getOrderId());
                        intent1.putExtra("statusId",notify.getStatusId());
                        context.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context, BreakDownOrderActivity.class);
                        intent2.putExtra("orderId",notify.getOrderId());
                        intent2.putExtra("statusId",notify.getStatusId());
                        context.startActivity(intent2);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_row_notification_img_food;
        TextView item_row_notification_tvOrderId;
        TextView item_row_notification_tvStatus;
        TextView item_row_notification_tvDecs;
        TextView item_row_notification_gapTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_row_notification_img_food = itemView.findViewById(R.id.item_row_notification_img_food);
            item_row_notification_tvOrderId = itemView.findViewById(R.id.item_row_notification_tvOrderId);
            item_row_notification_tvStatus = itemView.findViewById(R.id.item_row_notification_tvStatus);
            item_row_notification_tvDecs = itemView.findViewById(R.id.item_row_notification_tvDecs);
            item_row_notification_gapTime = itemView.findViewById(R.id.item_row_notification_gapTime);
        }
    }
}
