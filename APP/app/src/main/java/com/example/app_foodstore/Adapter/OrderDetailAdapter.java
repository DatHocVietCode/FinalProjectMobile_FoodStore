package com.example.app_foodstore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.Model.ProductInOrderDTO;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    Context context;
    List<ProductInOrderDTO> productInOrderDTOList;
    boolean isRating = false;

    public OrderDetailAdapter(Context context, List<ProductInOrderDTO> productInOrderDTOList, boolean isRating) {
        this.context = context;
        this.productInOrderDTOList = productInOrderDTOList;
        this.isRating = isRating;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderDetailAdapter.OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        holder.tvFoodName.setText(productInOrderDTOList.get(position).getFoodName());
        holder.tvNumber.setText("x" + productInOrderDTOList.get(position).getQuantity());
        holder.tvPrice.setText(productInOrderDTOList.get(position).getPrice() + "VND");
        if (isRating)
        {
            holder.img_Rate.setVisibility(View.VISIBLE);
            holder.cl_rate.setVisibility(View.VISIBLE);
            holder.tvNumber.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.img_Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tạo Dialog
                    Dialog dialog = new Dialog(context);

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
                            Toast.makeText(context, "Rating: " + (position + 1), Toast.LENGTH_SHORT).show();
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
                            // Xử lý sự kiện lưu đánh giá
                            String comment = edtComment.getText().toString().trim();
                            Toast.makeText(context, "Comment: " + comment, Toast.LENGTH_SHORT).show();
                            dialog.dismiss(); // Đóng dialog sau khi nhấn OK
                        }
                    });

                    // Hiển thị Dialog
                    dialog.show();
                }
            });
        }
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
        return productInOrderDTOList.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvNumber, tvPrice;
        ImageView img_Rate;
        ConstraintLayout cl_rate;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.item_order_detail_tv_foodName);
            tvNumber = itemView.findViewById(R.id.item_order_detail_tv_number);
            tvPrice = itemView.findViewById(R.id.item_order_detail_tv_price);
            img_Rate = itemView.findViewById(R.id.item_order_detail_img_rate);
            cl_rate = itemView.findViewById(R.id.item_order_detail_cl_rate);
        }
    }
}
