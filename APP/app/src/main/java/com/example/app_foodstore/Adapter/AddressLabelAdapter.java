package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.R;

import java.util.List;

public class AddressLabelAdapter extends RecyclerView.Adapter<AddressLabelAdapter.AddressLabelHolder> {
    private Context context;
    private List<String> keywordList;
    private int selectedPosition = 0;
    private OnLabelSelectedListener listener;

    // Interface callback để thông báo label được chọn
    public interface OnLabelSelectedListener {
        void onLabelSelected(String label);
    }

    // Constructor có thêm tham số listener
    public AddressLabelAdapter(Context context, List<String> keywordList, OnLabelSelectedListener listener) {
        this.context = context;
        this.keywordList = keywordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressLabelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_keyword, parent, false);
        return new AddressLabelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressLabelHolder holder, int position) {
        String label = keywordList.get(position);
        holder.tv_keyword.setText(label);

        // Set background và màu chữ tùy theo item được chọn
        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.rectangle_corner_radius_ff7622);
            holder.tv_keyword.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.itemView.setBackgroundResource(R.drawable.rectangle_corner_radius_bg_white);
            holder.tv_keyword.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.itemView.setOnClickListener(view -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && selectedPosition != pos) {
                selectedPosition = pos;
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onLabelSelected(keywordList.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return keywordList.size();
    }

    public static class AddressLabelHolder extends RecyclerView.ViewHolder {
        TextView tv_keyword;

        public AddressLabelHolder(@NonNull View itemView) {
            super(itemView);
            tv_keyword = itemView.findViewById(R.id.fragment_keyword_tv_recentKeyWord);
        }
    }
}
