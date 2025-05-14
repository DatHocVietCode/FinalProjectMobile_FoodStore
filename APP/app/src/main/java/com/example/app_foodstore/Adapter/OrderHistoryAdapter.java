package com.example.app_foodstore.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Activity.PaymentActivity;
import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.OrderViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    Context context;
    OrderViewModel orderViewModel;

    public OrderHistoryAdapter(Context context, OrderViewModel orderViewModel) {
        this.context = context;
        this.orderViewModel = orderViewModel;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_item_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        OrderModel orderModel = orderViewModel.getHistoryOrders().getValue().get(position);
        //holder.tv_orderDate.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(orderModel.getOrderDate()));
        holder.tv_orderDate.setText("01-01-2025 12:00"); // Giá trị cứng
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
        holder.btn_reOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khởi tạo BottomSheetDialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View sheetView = View.inflate(context, R.layout.order_detail_bottomsheet, null);
                bottomSheetDialog.setContentView(sheetView);

                // Ánh xạ RecyclerView và Button
                RecyclerView recyclerView = sheetView.findViewById(R.id.recycler_order_detail);
                MaterialButton btnReOrder = sheetView.findViewById(R.id.btn_reorder);
                MaterialButton btnCancel = sheetView.findViewById(R.id.btn_cancel);

                // Tạo dữ liệu giả để test giao diện
                List<OrderDetailModel> mockOrderDetails = new ArrayList<>();
                mockOrderDetails.add(new OrderDetailModel(1, "Pizza Margherita", 100000, 2));
                mockOrderDetails.add(new OrderDetailModel(2, "Spaghetti Bolognese", 75000, 1));
                mockOrderDetails.add(new OrderDetailModel(3, "Caesar Salad", 50000, 3));
                mockOrderDetails.add(new OrderDetailModel(4, "Garlic Bread", 30000, 2));

                // Cấu hình RecyclerView với Adapter
                OrderDetailAdapter adapter = new OrderDetailAdapter(context, mockOrderDetails);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);

                // Sự kiện khi nhấn nút ReOrder
                btnReOrder.setOnClickListener(v -> {
                    Intent intent = new Intent(context, PaymentActivity.class);
                    context.startActivity(intent);
                    bottomSheetDialog.dismiss();
                });

                // Sự kiện khi nhấn nút Cancel
                btnCancel.setOnClickListener(v -> {
                    bottomSheetDialog.dismiss();
                });

                // Hiển thị BottomSheetDialog
                bottomSheetDialog.show();
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
        return orderViewModel.getHistoryOrders().getValue().size();
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
