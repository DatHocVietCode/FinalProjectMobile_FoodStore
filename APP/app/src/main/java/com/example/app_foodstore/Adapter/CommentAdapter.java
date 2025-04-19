package com.example.app_foodstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.CommentModel;
import com.example.app_foodstore.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    Context context;
    List<CommentModel> commentList;

    public CommentAdapter(List<CommentModel> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        // Lấy đối tượng CommentModel từ danh sách
        CommentModel comment = commentList.get(position);

        // Gán dữ liệu vào các views trong ViewHolder
        holder.name.setText(comment.getName());
        holder.date.setText(comment.getDateCreated().toString());
        holder.comment.setText(comment.getContent());
        holder.rating.setText(String.valueOf(comment.getRating()));

        // Nếu bạn có một URL avatar hoặc drawable, bạn sẽ cần tải vào CircleImageView
        // Ví dụ nếu bạn sử dụng Glide để tải hình ảnh
        Glide.with(context)
                .load(comment.getAvatar())  // Giả sử bạn có phương thức getAvatarUrl trong CommentModel
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        TextView name, date, comment, rating;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.row_comment_avatar);
            name = itemView.findViewById(R.id.row_comment_tv_name);
            date = itemView.findViewById(R.id.row_comment_tv_dateCreated);
            comment = itemView.findViewById(R.id.row_comment_tv_comment);
            rating = itemView.findViewById(R.id.row_comment_rating_tv_rating);
        }
    }
}
