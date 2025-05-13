package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.OrderDetailAdapter;
import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BreakDownOrderActivity extends AppCompatActivity {
    RecyclerView rcOrderDetail;
    OrderModel orderModel;
    List<OrderDetailModel> listOrder;
    TextView btnToggleOrderInfo;
    LinearLayout cardOrderInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown_order);

        AnhXa();
    }

    private void AnhXa() {
        getOrder();
        setupRc();
        setupToggleBtn();
    }

    private void setupToggleBtn() {
        btnToggleOrderInfo = findViewById(R.id.btnToggleOrderInfo);
        cardOrderInfo = findViewById(R.id.orderInfoContainer);

        btnToggleOrderInfo.setOnClickListener(v -> {
            if (cardOrderInfo.getVisibility() == View.GONE) {
                cardOrderInfo.setVisibility(View.VISIBLE);
                btnToggleOrderInfo.setText("Order Information ▲");
            } else {
                cardOrderInfo.setVisibility(View.GONE);
                btnToggleOrderInfo.setText("Order Information ▼");
            }
        });
    }

    private void getOrder() {
        // Lấy từ API
        orderModel = new OrderModel(
                true,                    // isCompleted
                new Date(),              // orderDate (thời gian hiện tại)
                123456,                  // orderId
                1,                       // paymentMethod (1 = Credit Card)
                1,                       // orderStatus (1 = Ongoing)
                500.75f                  // totalAmount
        );
        listOrder = new ArrayList<>();
        listOrder.add(new OrderDetailModel(1, "Burger", 50000, 1));
        listOrder.add(new OrderDetailModel(2, "Pizza", 150000,1 ));
        listOrder.add(new OrderDetailModel(3, "Soda", 10000, 3));
        listOrder.add(new OrderDetailModel(4, "French Fries", 30000, 2));
        listOrder.add(new OrderDetailModel(1, "Burger", 50000, 1));
        listOrder.add(new OrderDetailModel(2, "Pizza", 150000,1 ));
        listOrder.add(new OrderDetailModel(3, "Soda", 10000, 3));
        listOrder.add(new OrderDetailModel(4, "French Fries", 30000, 2));
    }

    private void setupRc() {
        rcOrderDetail = findViewById(R.id.trackOrder_rc_orderDetail);
        OrderDetailAdapter adapter = new OrderDetailAdapter(this, listOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcOrderDetail.setAdapter(adapter);
        rcOrderDetail.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
}
