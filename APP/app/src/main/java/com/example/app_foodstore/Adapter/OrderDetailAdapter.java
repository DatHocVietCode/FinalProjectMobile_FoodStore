package com.example.app_foodstore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Utils.UserUtils;
import com.example.app_foodstore.Model.ProductInOrderDTO;
import com.example.app_foodstore.Model.request.CommentRequestDTO;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.CommentViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    Context context;
    List<ProductInOrderDTO> productInOrderDTOList;
    Integer start;
    boolean isRating = false;
    CommentViewModel commentViewModel;
    Long currentProductId;
    int currentStar = 3;
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
        ProductInOrderDTO product = productInOrderDTOList.get(holder.getAdapterPosition());
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

                    int filledStar = R.drawable.i_star_filled;
                    int unfilledStar = R.drawable.i_star;

// Gán sự kiện click cho sao
                    View.OnClickListener starClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = listStar.indexOf(v);
                            currentStar = position + 1;
                            for (int i = 0; i < listStar.size(); i++) {
                                if (i <= position) {
                                    listStar.get(i).setImageResource(filledStar);
                                    listStar.get(i).setTag("filled");
                                } else {
                                    listStar.get(i).setImageResource(unfilledStar);
                                    listStar.get(i).setTag("unfilled");
                                }
                            }
                        }
                    };

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

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String comment = edtComment.getText().toString();
                            if (comment.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập nội dung đánh giá!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            /*int rate = 0;
                            for (ImageView star : listStar) {
                                Object tag = star.getTag();
                                if (tag != null && tag.equals("filled")) {
                                    rate++;
                                }
                            }*/
                            Log.d("countingStar", "onClick: " + currentStar);

                            CommentRequestDTO request = new CommentRequestDTO(product.getIdProduct(), comment, currentStar);
                            commentViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CommentViewModel.class);
                            // Gửi comment qua ViewModel
                            commentViewModel.sendComment(UserUtils.getTokenFromPreferences(context), request);

                            // Theo dõi kết quả trả về
                            commentViewModel.getCommentResponse().observe((LifecycleOwner) context, new Observer<BaseResponse<Void>>() {
                                @Override
                                public void onChanged(BaseResponse<Void> response) {
                                    if (response != null && response.getStatus() == 201) {
                                        Toast.makeText(context, "Đánh giá đã được gửi!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Lỗi: " + (response != null ? response.getMessage() : "Không xác định"), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
