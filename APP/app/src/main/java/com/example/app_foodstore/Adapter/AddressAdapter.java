package com.example.app_foodstore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.response.AddressResponse;
import com.example.app_foodstore.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private final Context context;
    private final List<AddressResponse> listAddress;
    private final OnAddressDeleteListener deleteListener;
    private final OnItemClickListener itemClickListener;

    public AddressAdapter(Context context,
                          List<AddressResponse> addressList,
                          OnAddressDeleteListener deleteListener,
                          OnItemClickListener itemClickListener) {
        this.context = context;
        this.listAddress = addressList;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
    }

    // Interface khi nhấn xóa địa chỉ
    public interface OnAddressDeleteListener {
        void onDeleteAddress(Long addressId, int position);
    }

    // Interface khi click chọn địa chỉ
    public interface OnItemClickListener {
        void onItemClick(AddressResponse address);
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressResponse address = listAddress.get(position);

        // Hiển thị loại địa chỉ
        holder.tv_type.setText(address.getAddressType().toString());
        holder.tv_address.setText(address.getAddress());

        // Hiển thị icon tương ứng
        if ("HOME".equalsIgnoreCase(address.getAddressType().toString())) {
            holder.img_type.setImageResource(R.drawable.bg_home_location);
        } else if ("WORK".equalsIgnoreCase(address.getAddressType().toString())) {
            holder.img_type.setImageResource(R.drawable.bg_office_location);
        } else {
            holder.img_type.setImageResource(R.drawable.bg_other_location);
        }

        // Click vào item để trả về địa chỉ
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(address);
            }
        });

        // Click vào nút xóa
        holder.img_delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa địa chỉ này không?")
                    .setPositiveButton("Yes", (dialog1, which) -> {
                        if (deleteListener != null) {
                            deleteListener.onDeleteAddress(address.getId(), holder.getAdapterPosition());
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listAddress.size();
    }

    public void removeItem(int position) {
        listAddress.remove(position);
        notifyItemRemoved(position);
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        ImageView img_type, img_delete, img_edit;
        TextView tv_type, tv_address;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            img_type = itemView.findViewById(R.id.item_row_address_img_type);
            img_delete = itemView.findViewById(R.id.item_row_address_img_delete);
            img_edit = itemView.findViewById(R.id.item_row_address_img_edit);
            tv_type = itemView.findViewById(R.id.item_row_address_tv_type);
            tv_address = itemView.findViewById(R.id.item_row_address_tv_address);
        }
    }
}
