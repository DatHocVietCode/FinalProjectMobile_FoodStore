package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.PaymentInterfaceModel;
import com.example.app_foodstore.R;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder> {
    Context context;
    List<PaymentInterfaceModel> paymentInterfaceModelList;
    public interface OnPaymentMethodSelectedListener {
        void onMethodSelected(int position);
    }
    private OnPaymentMethodSelectedListener listener;
    public PaymentMethodAdapter(Context context, List<PaymentInterfaceModel> paymentInterfaceModelList, OnPaymentMethodSelectedListener listener) {
        this.context = context;
        this.paymentInterfaceModelList = paymentInterfaceModelList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public PaymentMethodAdapter.PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment_method, parent, false);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodAdapter.PaymentMethodViewHolder holder, int position) {
        PaymentInterfaceModel paymentInterfaceModel = paymentInterfaceModelList.get(position);
        holder.name_method.setText(paymentInterfaceModel.getName_method());
        if (paymentInterfaceModel.isChecked())
        {
            holder.img_method.setBackgroundResource(R.drawable.rectangle_corner_radius_stroke_ff7622);
            holder.img_method.setImageResource(paymentInterfaceModel.getImg_method_selected());
            holder.img_isChecked.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.img_method.setBackgroundResource(R.drawable.rectangle_corner_radius);
            holder.img_method.setImageResource(paymentInterfaceModel.getImg_method_unselected());
            holder.img_isChecked.setVisibility(View.INVISIBLE);
        }
        holder.payment_method_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đặt tất cả là chưa chọn
                for (int i = 0; i < paymentInterfaceModelList.size(); i++) {
                    paymentInterfaceModelList.get(i).setChecked(false);
                }

                // Đặt item hiện tại là đã chọn
                paymentInterfaceModel.setChecked(true);
                if (listener != null) {
                    listener.onMethodSelected(holder.getAdapterPosition());
                }
                // Cập nhật UI toàn bộ adapter
                notifyDataSetChanged(); // Đảm bảo toàn bộ item được cập nhật trạng thái UI
            }
        });

        if (paymentInterfaceModel.isChecked()) {
            holder.img_method.setBackgroundResource(R.drawable.rectangle_corner_radius_stroke_ff7622);
            holder.img_method.setImageResource(paymentInterfaceModel.getImg_method_selected());
            holder.img_isChecked.setVisibility(View.VISIBLE);
        } else {
            holder.img_method.setBackgroundResource(R.drawable.rectangle_corner_radius);
            holder.img_method.setImageResource(paymentInterfaceModel.getImg_method_unselected());
            holder.img_isChecked.setVisibility(View.INVISIBLE);
        }

    }
    public void setCheckedPosition(int position) {
        for (int i = 0; i < paymentInterfaceModelList.size(); i++) {
            paymentInterfaceModelList.get(i).setChecked(i == position);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return paymentInterfaceModelList.size();
    }

    public class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
        ImageView img_method;
        ImageView img_isChecked;
        TextView name_method;
        ConstraintLayout payment_method_container;
        public PaymentMethodViewHolder(@NonNull View itemView) {
            super(itemView);
            img_method = itemView.findViewById(R.id.payment_cash_image);
            img_isChecked = itemView.findViewById(R.id.payment_cash_check);
            name_method = itemView.findViewById(R.id.payment_cash_text);
            payment_method_container = itemView.findViewById(R.id.payment_method_container);
        }
    }
}
