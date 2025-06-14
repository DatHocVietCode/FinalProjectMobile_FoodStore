package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Model.request.OTPRequestDTO;
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
        btnConfirm = findViewById(R.id.otpScreen_btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                String email = sharedPreferences.getString("email", null);
                String otpToken = sharedPreferences.getString("otpToken", null);
                et1 = findViewById(R.id.otp_1);
                et2 = findViewById(R.id.otp_2);
                et3 = findViewById(R.id.otp_3);
                et4 = findViewById(R.id.otp_4);
                et5 = findViewById(R.id.otp_5);
                et6 = findViewById(R.id.otp_6);
                String otpCode = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString()
                        + et5.getText().toString() + et6.getText().toString();
                OTPRequestDTO otpRequestDTO = new OTPRequestDTO(email, otpCode, otpToken);

                authViewModel.verifyOTP(otpRequestDTO).observe(OTPActivity.this, message -> {
                    if (message != null) {
                        Toast.makeText(OTPActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(OTPActivity.this, "Invalid OTP!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

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
