package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.R;

import java.util.List;

public class VoucherSpinnerAdapter extends ArrayAdapter<VoucherModel> {
    Context context;
    List<VoucherModel> voucherList;
    private OnVoucherSelectedListener listener;
    public interface OnVoucherSelectedListener {
        void onVoucherSelected(VoucherModel voucher, int position);
    }

    public VoucherSpinnerAdapter(@NonNull Context context, List<VoucherModel> voucherList, OnVoucherSelectedListener listener) {
        super(context, 0, voucherList);
        this.context = context;
        this.voucherList = voucherList;
        this.listener = listener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout cho item trong spinner
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_voucher_item, parent, false);
        }

        VoucherModel voucher = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);
        textView.setText(voucher.getName());

        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Inflate layout cho dropdown
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_voucher_item, parent, false);
        }

        VoucherModel voucher = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);
        textView.setText(voucher.getName());
        // Gắn sự kiện click
        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVoucherSelected(voucher, position);
            }
        });
        return convertView;
    }
}
