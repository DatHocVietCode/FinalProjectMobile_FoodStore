package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.AddressModel;
import com.example.app_foodstore.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    Context context;
    List<AddressModel> listAddress;

    public AddressAdapter(Context context, List<AddressModel> listAddress) {
        this.context = context;
        this.listAddress = listAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_address, parent, false);
        AddressViewHolder holder = new AddressViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.AddressViewHolder holder, int position) {
        AddressModel addressModel = listAddress.get(position);
        if (addressModel.getType().equals("HOME")) {
            holder.img_type.setImageResource(R.drawable.bg_home_location);
        }
        if (addressModel.getType().equals("WORK")) {
            holder.img_type.setImageResource(R.drawable.bg_office_location);
        }
        if (addressModel.getType().equals("OTHER")) {
            holder.img_type.setImageResource(R.drawable.bg_other_location);
        }
        holder.tv_type.setText(addressModel.getType());
        holder.tv_address.setText(addressModel.getAddress());
    }

    @Override
    public int getItemCount() {
        return listAddress.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        ImageView img_type;
        ImageView img_delete;
        ImageView img_edit;
        TextView tv_type;
        TextView tv_address;

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
