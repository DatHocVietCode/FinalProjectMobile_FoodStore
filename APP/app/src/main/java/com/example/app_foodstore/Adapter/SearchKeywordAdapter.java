package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.SearchKeywordModel;
import com.example.app_foodstore.R;

import java.util.List;

public class SearchKeywordAdapter extends RecyclerView.Adapter<SearchKeywordAdapter.ViewHolder> {
    Context context;
    List<SearchKeywordModel> keywords;
    public interface OnKeywordClickListener {
        void onKeywordClick(String keyword);
    }
    private OnKeywordClickListener listener;

    public void setOnKeywordClickListener(OnKeywordClickListener listener) {
        this.listener = listener;
    }
    public SearchKeywordAdapter(Context context, List<SearchKeywordModel> keywords) {
        this.context = context;
        this.keywords = keywords;
    }

    public List<SearchKeywordModel> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<SearchKeywordModel> keywords) {
        this.keywords = keywords;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchKeywordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_keyword, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchKeywordAdapter.ViewHolder holder, int position) {
        SearchKeywordModel keyword = keywords.get(position);
        holder.tv_keyword.setText(keyword.getKeyword());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onKeywordClick(keyword.getKeyword());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_keyword;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_keyword = itemView.findViewById(R.id.fragment_keyword_tv_recentKeyWord);
        }
    }
}
