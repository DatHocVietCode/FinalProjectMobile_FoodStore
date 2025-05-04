package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.R;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    Context context;
    List<VoucherModel> list;

    public VoucherAdapter(Context context, List<VoucherModel> list, int tabNum) {
        this.context = context;
        this.list = list;
        this.tabNum = tabNum;
    }

    int tabNum;
    @NonNull
    @Override
    public VoucherAdapter.VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.VoucherViewHolder holder, int position) {
        VoucherModel voucherModel = list.get(position);
        holder.tv_voucherTitle.setText("Discount: " + voucherModel.getDiscount());
        holder.tv_voucherDescription.setText("Apply for order from " + voucherModel.getMinAmount() + "$");
        holder.tv_name.setText(voucherModel.getVoucherName());
        holder.tv_name.setSelected(true);
        holder.tv_voucherDescription.setSelected(true);
        switch (tabNum)
        {
            case 0:
                holder.tv_delete.setVisibility(View.VISIBLE);
                holder.tv_save.setVisibility(View.GONE);
                break;
            case 1:
                holder.tv_delete.setVisibility(View.GONE);
                holder.tv_save.setVisibility(View.VISIBLE);
        }
        // Xử lý click cho Save
        holder.tv_save.setOnClickListener(v -> animateAndRemove(holder.getAdapterPosition(), holder.itemView));

        // Xử lý click cho Delete
        holder.tv_delete.setOnClickListener(v -> animateAndRemove(holder.getAdapterPosition(), holder.itemView));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView tv_voucherTitle, tv_voucherDescription, tv_save, tv_delete, tv_name;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_voucherTitle = itemView.findViewById(R.id.item_row_voucher_tv_Title);
            tv_voucherDescription = itemView.findViewById(R.id.textViewDescription);
            tv_save = itemView.findViewById(R.id.item_row_voucher_tvSave);
            tv_delete = itemView.findViewById(R.id.item_row_voucher_tvDelete);
            tv_name = itemView.findViewById(R.id.item_row_voucher_tv_Name);
        }
    }
    private void animateAndRemove(int position, View view) {
        view.animate()
                .alpha(0f)
                .setDuration(500)
                .withEndAction(() -> {
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                })
                .start();
    }

}
