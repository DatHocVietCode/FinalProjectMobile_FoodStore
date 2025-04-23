package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.R;

public class PaymentActivity extends AppCompatActivity {
    boolean selected = true; // hoặc false tùy trạng thái

    ImageView imageView;
    LinearLayout layout;
    ImageView checkImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        AnhXa();
    }

    private void AnhXa() {
        setupPayment();
    }

    private void setupPayment() {
        imageView = findViewById(R.id.payment_cash_image);
        layout = findViewById(R.id.payment_cash_container);
        checkImage = findViewById(R.id.payment_cash_check);
        if (selected) {
            imageView.setImageResource(R.drawable.cash_selected);
            imageView.setBackgroundResource(R.drawable.rectangle_corner_radius_stroke_ff7622);
            checkImage.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.drawable.cash_unselected);
            imageView.setBackgroundResource(R.drawable.rectangle_corner_radius);
            checkImage.setVisibility(View.INVISIBLE);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected)
                {
                    imageView.setImageResource(R.drawable.cash_unselected);
                    imageView.setBackgroundResource(R.drawable.rectangle_corner_radius);
                    checkImage.setVisibility(View.INVISIBLE);
                    selected = false;
                }
                else
                {
                    imageView.setImageResource(R.drawable.cash_selected);
                    imageView.setBackgroundResource(R.drawable.rectangle_corner_radius_stroke_ff7622);
                    checkImage.setVisibility(View.VISIBLE);
                    selected = true;
                }
            }
        });
    }
}
