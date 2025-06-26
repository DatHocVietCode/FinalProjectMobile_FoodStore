package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.R;

public class PaymentNotificationActivity extends AppCompatActivity {
    Button btnTrackOrder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notifycation);

        AnhXa();
    }

    private void AnhXa() {
        setupBtn();
    }

    private void setupBtn() {
        btnTrackOrder = findViewById(R.id.activity_payment_notification_btnTrackrder);
        btnTrackOrder.setOnClickListener(v -> {
            Intent homeIntent = new Intent(PaymentNotificationActivity.this, HomeScreenActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);

            // Điều hướng ngay lập tức đến TrackOrderActivity
            Intent trackOrderIntent = new Intent(PaymentNotificationActivity.this, HomeScreenActivity.class);
            startActivity(trackOrderIntent);

            // Kết thúc PaymentNotificationActivity để không còn nằm trong stack
            finish();

        });
    }
}
