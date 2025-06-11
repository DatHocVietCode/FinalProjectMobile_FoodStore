package com.example.app_foodstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.R;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        AnhXa();
    }
    private void AnhXa()
    {
        setUpBtnLogin();
        setUpLogicPassword();
        setUpBtnSignUp();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpLogicPassword() {
        EditText passwordEditText = findViewById(R.id.signup_etPassword);
        EditText rePasswordEditText = findViewById(R.id.signup_etRePassword);

        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2; // Vị trí drawableEnd
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    // Lấy trạng thái hiện tại
                    int inputType = passwordEditText.getInputType();
                    if ((inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        // Đang hiện => ẩn mật khẩu
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_icon, 0);
                    } else {
                        // Đang ẩn => hiện mật khẩu
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.i_password_hide, 0);
                    }

                    // Giữ con trỏ ở cuối
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });

    }

    private void setUpBtnSignUp() {
        String username = findViewById(R.id.signup_etUsername).toString().trim();
        String email = findViewById(R.id.signup_etEmail).toString().trim();
        String password = findViewById(R.id.signup_etPassword).toString().trim();
        String rePassword = findViewById(R.id.signup_etRePassword).toString().trim();
    }

    private void setUpBtnLogin() {
        findViewById(R.id.signup_tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
