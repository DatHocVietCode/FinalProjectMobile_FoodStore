package com.example.app_foodstore.Adapter;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;


import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Activity.PaymentActivity;
import com.example.app_foodstore.Utils.UserUtils;
import com.example.app_foodstore.Interface.AddressSelectionListener;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.AddressViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    Context context;
    Activity activity;
    AddressSelectionListener listener;
    private List<MyOrderPendingDTO> orderList = new ArrayList<>();

    public OrderHistoryAdapter(Context context, List<MyOrderPendingDTO> orderList,  AddressSelectionListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;

    }

    AddressViewModel addressViewModel;
    TextView tvEditAddress, tvAddress;
    EditText etAddress;
    Long idAddress;
    Button btn_reOrder, btn_rating;
    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order_item_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        MyOrderPendingDTO order = orderList.get(holder.getAdapterPosition());

        String createdDate = order.getCreated();
        String formattedDate = formatDateString(createdDate);
        holder.tv_orderDate.setText(formattedDate);

        // Set status visibility
        String status = order.getStatus();
        if (status != null) {
            if ("COMPLETED".equalsIgnoreCase(status)) {
                holder.tv_orderCompleted.setVisibility(View.VISIBLE);
                holder.tv_orderCanceled.setVisibility(View.GONE);
            } else if ("CANCEL".equalsIgnoreCase(status)) {
                holder.tv_orderCompleted.setVisibility(View.GONE);
                holder.tv_orderCanceled.setVisibility(View.VISIBLE);
            } else {
                holder.tv_orderCompleted.setVisibility(View.GONE);
                holder.tv_orderCanceled.setVisibility(View.GONE);
            }
        } else {
            holder.tv_orderCompleted.setVisibility(View.GONE);
            holder.tv_orderCanceled.setVisibility(View.GONE);
        }

        // Set other fields
        holder.tv_orderId.setText("#" + (order.getIdOrder() != null ? order.getIdOrder() : ""));
        holder.tv_foodName.setText(order.getProducts().get(0).getFoodName());
        holder.tv_totalprice.setText(String.valueOf(order.getTotalPrice()));
        holder.tv_category.setText((order.getProducts().get(0).getCategory()));
        holder.tv_items.setText(String.valueOf(order.getProducts().size()));
        Glide.with(holder.img_food.getContext()).load(IMG_URL + order.getProducts().get(0).getThumbnail()).into(holder.img_food);


        btn_reOrder = holder.itemView.findViewById(R.id.fragment_order_item_history_btn_reOrder);
        btn_reOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle re-order button click
                callBottomSheet(false,holder.getAdapterPosition());
            }
        });

        btn_rating = holder.itemView.findViewById(R.id.fragment_order_item_history_btn_rate);
        if(status.equals("CANCEL")){
            btn_rating.setVisibility(View.INVISIBLE);
        }
        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBottomSheet(true, holder.getAdapterPosition());
            }
        });
    }
    private void callBottomSheet(boolean isRating, int pos)
    {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View sheetView = View.inflate(context, R.layout.order_detail_bottomsheet, null);
        bottomSheetDialog.setContentView(sheetView);

        tvEditAddress = sheetView.findViewById(R.id.orderDetailBottomSheet_tv_editAdress);
        etAddress = sheetView.findViewById(R.id.orderDetailBottomSheet_et_address);
        tvAddress = sheetView.findViewById(R.id.orderDetailBottomSheet_tv_address);
        // Ánh xạ RecyclerView và Button
        RecyclerView recyclerView = sheetView.findViewById(R.id.recycler_order_detail);
        MaterialButton btnReOrder = sheetView.findViewById(R.id.btn_reorder);
        MaterialButton btnCancel = sheetView.findViewById(R.id.btn_cancel);


        addressViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(AddressViewModel.class);
        String token = UserUtils.getAccessToken(context);

        addressViewModel.getDefaultAddress(token).observe((LifecycleOwner) context, addressModel -> {
            if (addressModel != null) {
                idAddress = addressModel.getId();
                etAddress.setText(addressModel.getAddress());
            } else {
                etAddress.setText("Không có địa chỉ mặc định");
            }
        });

        OrderDetailAdapter adapter = new OrderDetailAdapter(context,orderList.get(pos).getProducts(), isRating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /*// Cấu hình RecyclerView với Adapter
        OrderDetailAdapter adapter = new OrderDetailAdapter(context, mockOrderDetails, isRating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/

        if (isRating)
        {
            btnReOrder.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            etAddress.setVisibility(View.GONE);
            tvEditAddress.setVisibility(View.GONE);
        }
        tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddressEditClicked(etAddress);
            }
        });
        // Sự kiện khi nhấn nút ReOrder
        btnReOrder.setOnClickListener(v -> {
            Intent intent = new Intent(context, PaymentActivity.class);
            intent.putExtra("idAddress", idAddress);
            intent.putExtra("order", orderList.get(pos).getTotalPrice());
            intent.putExtra("re-order",true);
            intent.putExtra("idOrder",orderList.get(pos).getIdOrder());
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
    private void loadDefaultAddress() {

    }
    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public void setData(List<MyOrderPendingDTO> data) {
        this.orderList = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    private String formatDateString(String isoDate) {
        if (isoDate == null) return "";
        try {
            // ISO 8601 format example: "2024-05-15T13:45:00"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = inputFormat.parse(isoDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return isoDate; // Nếu lỗi thì hiển thị nguyên bản
        }
    }

    static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_orderDate, tv_orderCompleted, tv_orderCanceled, tv_orderId,tv_foodName,tv_category,tv_items,tv_totalprice;
        ImageView img_food;
        Button btn_reOrder, btn_rate;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderDate = itemView.findViewById(R.id.fragment_order_item_history_tv_dateOrdered);
            tv_orderCompleted = itemView.findViewById(R.id.fragment_order_item_history_tv_status_completed);
            tv_orderCanceled = itemView.findViewById(R.id.fragment_order_item_history_tv_status_canceled);
            tv_orderId = itemView.findViewById(R.id.fragment_order_item_history_tv_foodID);
            tv_category = itemView.findViewById(R.id.fragment_order_item_history_tv_categoryName);
            tv_foodName = itemView.findViewById(R.id.fragment_order_item_history_tv_foodName);
            tv_items = itemView.findViewById(R.id.fragment_order_item_history_tv_itemCount);
            tv_totalprice = itemView.findViewById(R.id.fragment_order_item_history_tv_price);
            img_food = itemView.findViewById(R.id.fragment_order_item_history_img_food);
            btn_reOrder = itemView.findViewById(R.id.fragment_order_item_history_btn_reOrder);
            btn_rate = itemView.findViewById(R.id.fragment_order_item_history_btn_rate);
        }
    }
}
