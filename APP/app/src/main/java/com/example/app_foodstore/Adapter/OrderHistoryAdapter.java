package com.example.app_foodstore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    Context context;
    List<OrderModel> listOrder;

    public OrderHistoryAdapter(Context context, List<OrderModel> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_item_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        OrderModel orderModel = listOrder.get(position);
        holder.tv_orderDate.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(orderModel.getOrderDate()));
        if (orderModel.isCompleted())
        {
            holder.tv_orderCompleted.setVisibility(View.VISIBLE);
            holder.tv_orderCanceled.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_orderCompleted.setVisibility(View.GONE);
            holder.tv_orderCanceled.setVisibility(View.VISIBLE);
        }
        holder.tv_foodId.setText("#123");
        holder.tv_foodId.setPaintFlags(holder.tv_foodId.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Dialog
                Dialog dialog = new Dialog(view.getContext());

                // Gán layout cho Dialog
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_rating);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                // Ánh xạ các thành phần trong Dialog
                EditText edtComment = dialog.findViewById(R.id.dialog_rating_et_comment);
                Button btnCancel = dialog.findViewById(R.id.dialog_rating_btnCancel);
                Button btnOK = dialog.findViewById(R.id.dialog_rating_btnOK);
                List<ImageView> listStar = new ArrayList<>();
                listStar.add(dialog.findViewById(R.id.dialog_rating_star1));
                listStar.add(dialog.findViewById(R.id.dialog_rating_star2));
                listStar.add(dialog.findViewById(R.id.dialog_rating_star3));
                listStar.add(dialog.findViewById(R.id.dialog_rating_star4));
                listStar.add(dialog.findViewById(R.id.dialog_rating_star5));

                // Drawable cho ngôi sao (đã điền và chưa điền)
                int filledStar = R.drawable.i_star_filled;
                int unfilledStar = R.drawable.i_star;

                // Hàm cập nhật trạng thái ngôi sao
                View.OnClickListener starClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = listStar.indexOf(v); // Lấy vị trí của ngôi sao được click
                        rotateStar(position, listStar);
                        // Cập nhật trạng thái ngôi sao
                        for (int i = 0; i < listStar.size(); i++) {
                            if (i <= position) {
                                listStar.get(i).setImageResource(filledStar);
                            } else {
                                listStar.get(i).setImageResource(unfilledStar);
                            }
                        }

                        // In ra điểm được chọn (1-5)
                        Toast.makeText(view.getContext(), "Rating: " + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                };

                // Gán sự kiện click cho từng ngôi sao
                for (ImageView star : listStar) {
                    star.setOnClickListener(starClickListener);
                }

                // Bắt sự kiện Cancel
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); // Đóng dialog khi bấm Cancel
                    }
                });

                // Bắt sự kiện OK
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comment = edtComment.getText().toString().trim();
                        Toast.makeText(view.getContext(), "Comment: " + comment, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Đóng dialog sau khi nhấn OK
                    }
                });

                // Hiển thị Dialog
                dialog.show();
            }
        });

    }
    // Hàm tạo animation xoay
    private void rotateStar(int pos, List<ImageView> listStar) {
        for (int i = 0; i <= pos; i++) {
            ImageView view = listStar.get(i);
            view.animate()
                    .rotationBy(360)  // Xoay 360 độ
                    .setDuration(500) // Thời gian xoay là 300ms
                    .start();
        }
    }
    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_orderDate, tv_orderCompleted, tv_orderCanceled, tv_foodId;
        Button btn_rate, btn_reOrder;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderDate = itemView.findViewById(R.id.fragment_order_item_history_tv_dateOrdered);
            tv_orderCompleted = itemView.findViewById(R.id.fragment_order_item_history_tv_status_completed);
            tv_orderCanceled = itemView.findViewById(R.id.fragment_order_item_history_tv_status_canceled);
            tv_foodId = itemView.findViewById(R.id.fragment_order_item_history_tv_foodID);
            btn_rate = itemView.findViewById(R.id.fragment_order_item_history_btn_rate);
            btn_reOrder = itemView.findViewById(R.id.fragment_order_item_history_btn_reOrder);
        }
    }
}
