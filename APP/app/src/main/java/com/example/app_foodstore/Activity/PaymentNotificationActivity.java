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
            Intent intent = new Intent(PaymentNotificationActivity.this, TrackOrderActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
