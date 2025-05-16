package com.example.app_foodstore.Activity;

import android.animation.ObjectAnimator;
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
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BreakDownOrderActivity extends AppCompatActivity {
    RecyclerView rcOrderDetail;
    OrderModel orderModel;
    List<MyOrderPendingDTO> listOrder;
    TextView btnToggleOrderInfo, tvOrderId, tvOrderDate, tvPaymentMethod, tvTotalAmount
            , tvDeliverty, tvVoucher;
    LinearLayout cardOrderInfo;
    TextView tvTransactionStatus;
    MyOrderPendingDTO order;
    int statusId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown_order);

        AnhXa();
    }

    private void AnhXa() {
        getArgument();
        //getOrder();
        setupTransactionStatus();
        setupGeneralInformation();
        setupOtherInformation();
        setupRc();
        setupToggleBtn();
    }

    private void setupOtherInformation() {
        tvDeliverty = findViewById(R.id.tvDeliveryFee);
        tvVoucher = findViewById(R.id.tvVoucher);

        tvDeliverty.setText(order.getDeliveryFee() + " VND");
        tvVoucher.setText(order.getVoucher() + "VND");
    }

    private void setupGeneralInformation() {
        tvOrderId = findViewById(R.id.breakdownScreen_tvOrderId);
        tvOrderDate = findViewById(R.id.breakdownScreen_tvTransactionTime);
        tvPaymentMethod = findViewById(R.id.breakdownScreen_tvPaymentMethod);
        tvTotalAmount = findViewById(R.id.breakdownScreen_tvTotalAmount);

        tvOrderId.setText("#" + order.getIdOrder());
        String originalDate = order.getCreated();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Date date = inputFormat.parse(originalDate);
            String formattedDate = outputFormat.format(date);
            tvOrderDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            tvOrderDate.setText("Invalid Date");
        }
        tvPaymentMethod.setText(order.getPaymentMethod());
        tvTotalAmount.setText(order.getTotalPrice() + "VND");
    }

    private void getArgument() {
        if (getIntent().getExtras() != null) {
            order = (MyOrderPendingDTO) getIntent().getSerializableExtra("order");

            // Kiểm tra trạng thái và gán giá trị tương ứng
            if ("0".equals(order.getStatus()) || "1".equals(order.getStatus()) ||
                    "2".equals(order.getStatus()) || "3".equals(order.getStatus())) {
                statusId = 0;
            }
            if ("CANCEL".equals(order.getStatus()))
            {
                statusId = 2;
            }
            if ("COMPLETED".equals(order.getStatus()))
            {
                statusId = 1;
            }
        }

    }

    private void setupTransactionStatus() {
        tvTransactionStatus = findViewById(R.id.breakdownScreen_tvTransactionStatus);
        switch (statusId) {
            case 0:
                tvTransactionStatus.setText("Ongoing");
                tvTransactionStatus.setTextColor(getResources().getColor(R.color.onGoing));
                break;
            case 1:
                tvTransactionStatus.setText("Completed");
                tvTransactionStatus.setTextColor(getResources().getColor(R.color.completed));
                break;
            case 2:
                tvTransactionStatus.setText("Canceled");
                tvTransactionStatus.setTextColor(getResources().getColor(R.color.canceled));
                break;
        }
    }

    private void setupToggleBtn() {
        btnToggleOrderInfo = findViewById(R.id.btnToggleOrderInfo);
        cardOrderInfo = findViewById(R.id.orderInfoContainer);

        btnToggleOrderInfo.setOnClickListener(v -> {
            if (cardOrderInfo.getVisibility() == View.GONE) {
                // Hiện cardOrderInfo với animation
                cardOrderInfo.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(cardOrderInfo, "translationY", 200f, 0f).setDuration(300).start();
                btnToggleOrderInfo.setText("Order Information ▲");
            } else {
                // Ẩn cardOrderInfo với animation
                ObjectAnimator.ofFloat(cardOrderInfo, "translationY", 0f, 200f).setDuration(300).start();
                cardOrderInfo.postDelayed(() -> cardOrderInfo.setVisibility(View.GONE), 300); // Delay trước khi setVisibility GONE
                btnToggleOrderInfo.setText("Order Information ▼");
            }
        });
    }

    /*private void getOrder() {
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


    }*/
     /*private void getOrder() {
            if (order != null) {
                int idPaymentMethod;
                switch (order.getPaymentMethod()) {
                    case "COMPLETED":
                        idPaymentMethod = 1;
                        break;
                    case "CANCEL":
                        idPaymentMethod = 2;
                        break;
                    default:
                        idPaymentMethod = 0;
                        break;
                }
                // Lấy thông tin từ đối tượng order
                listOrder = new ArrayList<>();
                // Duyệt qua danh sách sản phẩm trong order
                if (order.getProducts() != null) {
                    for (var product : order.getProducts()) {
                        listOrder.add(new OrderDetailModel(
                                product.getIdProduct(),   // ID của sản phẩm
                                product.getFoodName(),                      // Tên sản phẩm
                                product.getPrice(),                         // Giá
                                product.getQuantity()                       // Số lượng
                        ));
                    }
                }
            }
        }*/

    private void setupRc() {
        rcOrderDetail = findViewById(R.id.trackOrder_rc_orderDetail);
        OrderDetailAdapter adapter = new OrderDetailAdapter(this, order.getProducts(), false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcOrderDetail.setAdapter(adapter);
        rcOrderDetail.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
}
