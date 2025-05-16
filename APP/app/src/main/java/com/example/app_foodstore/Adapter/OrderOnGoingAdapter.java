package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class OrderOnGoingAdapter extends RecyclerView.Adapter<OrderOnGoingAdapter.OrderViewHolder> {

    private List<MyOrderPendingDTO> orderList = new ArrayList<>();

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order_item_ongoing, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        MyOrderPendingDTO order = orderList.get(position);

        // Set tên category (ví dụ lấy từ order.getCategoryName())
        holder.tvCategoryName.setText(order.getProducts().get(0).getCategory() != null ? order.getProducts().get(0).getCategory() : "Unknown Category");

        // Set "Toio" TextView (nếu có logic thì bạn sửa lại, nếu chỉ text tĩnh thì có thể bỏ hoặc giữ nguyên)
        holder.tvToio.setText(order.getStatus());

        // Set tên món ăn
        holder.tvFoodName.setText(order.getProducts().get(0).getFoodName() != null ? order.getProducts().get(0).getFoodName() : "Unknown Food");

        // Set ID món ăn (ví dụ order.getFoodId())
        holder.tvFoodId.setText(order.getIdOrder() != null ? "#" + order.getIdOrder() : "#N/A");

        // Set giá món (giá ở dạng số hoặc String, bạn convert nếu cần)
        holder.tvPrice.setText(order.getTotalPrice() != null ? String.valueOf(order.getTotalPrice()) : "0");

        // Set số lượng món
        holder.tvItemCount.setText(order.getProducts() != null ? String.valueOf(order.getProducts().size()) : "0");
        Glide.with(holder.imgFood.getContext()).load(IMG_URL + order.getProducts().get(0).getThumbnail()).into(holder.imgFood);
        // Nếu bạn muốn hiển thị ảnh món ăn thì cần load hình từ url hoặc resource:
        // Ví dụ dùng Glide hoặc Picasso:


        // Hiện tại bạn có thể giữ mặc định hoặc set ảnh tĩnh nếu không có hình
        // holder.imgFood.setImageResource(R.drawable.food_sample);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setData(List<MyOrderPendingDTO> data) {
        this.orderList = data;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName, tvToio, tvFoodName, tvFoodId, tvPrice, tvItemCount;
        ImageView imgFood;
        Button btnTrack, btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_categoryName);
            tvToio = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_toio);
            tvFoodName = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_foodName);
            tvFoodId = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_foodID);
            tvPrice = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_price);
            tvItemCount = itemView.findViewById(R.id.fragment_order_item_ongoing_tv_itemCount);
            imgFood = itemView.findViewById(R.id.fragment_order_item_ongoing_imgFood);
            btnTrack = itemView.findViewById(R.id.fragment_order_item_ongoing_btn_track);
            btnCancel = itemView.findViewById(R.id.fragment_order_item_ongoing_btn_cancel);
        }
    }
}
