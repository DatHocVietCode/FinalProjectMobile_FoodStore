package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.AuthViewModel;
import com.google.android.material.button.MaterialButton;

public class OTPActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6;
    MaterialButton btnConfirm;
    TextView tvResend;
    AuthViewModel authViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        AnhXa();
    }

    private void AnhXa() {
        initViewModel();
        setUpBtnConfirm();
        setUpTvResend();
    }

    private void setUpBtnConfirm() {
    }

    private void setUpTvResend() {
        tvResend = findViewById(R.id.otpScreen_tvResend);
        tvResend.setOnClickListener(view -> {
            authViewModel.resendOTP().observe(OTPActivity.this, message -> {
                if (message != null) {
                    Toast.makeText(OTPActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }
}
