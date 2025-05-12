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
    Context context;
    List<String> keywordList;

    public AddressLabelAdapter(Context context, List<String> keywordList) {
        this.context = context;
        this.keywordList = keywordList;
    }

    @NonNull
    @Override
    public AddressLabelAdapter.AddressLabelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_keyword, parent, false);
        return new AddressLabelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressLabelAdapter.AddressLabelHolder holder, int position) {
        holder.tv_keyword.setText(keywordList.get(position));
    }

    @Override
    public int getItemCount() {
        return keywordList.size();
    }

    public class AddressLabelHolder extends RecyclerView.ViewHolder {
        TextView tv_keyword;
        public AddressLabelHolder(@NonNull View itemView) {
            super(itemView);
            tv_keyword = itemView.findViewById(R.id.fragment_keyword_tv_recentKeyWord);
        }
    }
}
