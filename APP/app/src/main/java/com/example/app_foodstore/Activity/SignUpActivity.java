package com.example.app_foodstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Model.request.UserSignUpRequest;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Utils.PasswordUtils;
import com.example.app_foodstore.ViewModel.AuthViewModel;
import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {
    AuthViewModel authViewModel;
    MaterialButton btnSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        AnhXa();
    }
    private void AnhXa()
    {
        initViewModel();
        setUpBtnLogin();
        setUpLogicPassword();
        setUpBtnSignUp();
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }


    private void setUpLogicPassword() {
        EditText passwordEditText = findViewById(R.id.signup_etPassword);
        EditText rePasswordEditText = findViewById(R.id.signup_etRePassword);

        setupPasswordToggle(passwordEditText, R.drawable.password_icon, R.drawable.i_password_hide);
        setupPasswordToggle(rePasswordEditText, R.drawable.password_icon, R.drawable.i_password_hide);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void setupPasswordToggle(EditText editText, int iconShow, int iconHide) {
        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    int inputType = editText.getInputType();
                    if ((inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconShow, 0);
                    } else {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconHide, 0);
                    }
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }


    private void setUpBtnSignUp() {
        btnSignUp = findViewById(R.id.signup_btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.signup_etUsername)).getText().toString().trim();
                String email = ((EditText) findViewById(R.id.signup_etEmail)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.signup_etPassword)).getText().toString().trim();
                String rePassword = ((EditText) findViewById(R.id.signup_etRePassword)).getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PasswordUtils.isSamePassword(password, rePassword)) {
                    Toast.makeText(SignUpActivity.this, "Please recheck your Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!PasswordUtils.isPasswordValid(password)) {
                    Toast.makeText(SignUpActivity.this, "Your password is invalid!", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, username, password);

                authViewModel.signUp(userSignUpRequest).observe(SignUpActivity.this, userSignUpResponse -> {
                    if (userSignUpResponse != null)
                    {
                        Toast.makeText(SignUpActivity.this, "Sign up successfully! Activate your account by by OTP!", Toast.LENGTH_SHORT);
                        //Intent intent = new Intent(SignUpActivity.this)
                    }
                });
            }
        });

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
