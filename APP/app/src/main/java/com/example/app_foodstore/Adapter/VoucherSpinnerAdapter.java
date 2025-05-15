package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.R;

import java.util.List;

public class VoucherSpinnerAdapter extends ArrayAdapter<VoucherModel> {
    private Context context;
    private List<VoucherModel> voucherList;

    public VoucherSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<VoucherModel> voucherList) {
        super(context, resource, voucherList);
        this.context = context;
        this.voucherList = voucherList;
    }

    // View hiển thị voucher đã chọn (trong Spinner khi chưa mở dropdown)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_voucher_item, parent, false);
        }
        VoucherModel voucher = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);
        if (voucher != null) {
            textView.setText(voucher.getName());
        } else {
            textView.setText("");
        }
        return convertView;
    }

    // View hiển thị voucher trong dropdown danh sách
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_voucher_item, parent, false);
        }
        VoucherModel voucher = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);
        if (voucher != null) {
            textView.setText(voucher.getName());
        } else {
            textView.setText("");
        }
        return convertView;
    }
}
