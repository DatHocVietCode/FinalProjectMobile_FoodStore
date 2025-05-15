package com.example.app_foodstore.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.NotificationAdapter;
import com.example.app_foodstore.Model.NotificationModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rv_notification;
    List<NotificationModel> list;
    NotificationAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        AnhXa();
    }

    private void AnhXa() {
        setupRc();
    }

    private void setupRc() {
        getNotification();
        rv_notification = findViewById(R.id.notificationScreen_rv_notification);
        adapter = new NotificationAdapter(this,list);
        rv_notification.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv_notification.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    private void getNotification() {
        list = new ArrayList<>();
        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();

        // Thêm 5 thông báo với các khoảng thời gian khác nhau:
        // 5 phút trước
        calendar.add(Calendar.MINUTE, -5);
        list.add(new NotificationModel("ORD123", 0, "Your order is being processed", calendar.getTime()));

        // 2 giờ trước
        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -2);
        list.add(new NotificationModel("ORD124", 1, "Your order has been delivered", calendar.getTime()));

        // 1 ngày trước
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        list.add(new NotificationModel("ORD125", 2, "Your order was canceled", calendar.getTime()));

        // 3 ngày trước
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        list.add(new NotificationModel("ORD126", 0, "Your order is being processed", calendar.getTime()));

        // 1 tuần trước
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        list.add(new NotificationModel("ORD127", 1, "Your order has been delivered", calendar.getTime()));

    }
}
