package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.LoginViewModel;
import com.google.android.material.button.MaterialButton;
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.username_login);
            EditText passwordEditText = findViewById(R.id.password_login);

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            UserLoginReq req = new UserLoginReq(username, password);

            loginViewModel.login(req).observe(this, response -> {
                if (response != null) {
                    saveLogin(response);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeScreenActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        findViewById(R.id.tv_guest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }
    private void saveLogin(UserLoginRes res) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", res.getAccessToken());
        editor.putString("username", res.getUserLogin().getUsername());
        editor.putString("fullname", res.getUserLogin().getFullname());
        editor.putBoolean("is_logged_in", true);
        editor.putLong("user_id", res.getUserLogin().getId());
        editor.apply();
    }
}
