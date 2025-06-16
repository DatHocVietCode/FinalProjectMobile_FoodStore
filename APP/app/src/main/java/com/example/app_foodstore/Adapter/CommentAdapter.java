package com.example.app_foodstore.Adapter;



import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

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
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentModel comment = commentList.get(position);

        holder.name.setText(comment.getName() != null ? comment.getName() : "Ẩn danh");
        holder.date.setText(comment.getCreated_at() != null ? comment.getCreated_at() : "Không rõ ngày");
        holder.comment.setText(comment.getContent() != null ? comment.getContent() : "Không có nội dung");
        holder.rating.setText(comment.getRating() != null ? String.valueOf(comment.getRating()) : "0");

        Glide.with(context)
                .load(IMG_URL + comment.getAvatar_user())
                .placeholder(R.drawable.personal_info)  // ảnh mặc định khi đang load
                .error(R.drawable.personal_info)              // ảnh nếu lỗi
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
