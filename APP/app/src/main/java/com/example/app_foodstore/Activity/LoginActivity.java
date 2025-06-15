package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();
    }

    private void AnhXa()
    {
        initViewModel();
        setUpBtnLogin();
        setUpBtnLoginAsGuest();
        setUpBtnSignUp();
    }

    private void setUpBtnSignUp() {
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpBtnLoginAsGuest() {
        findViewById(R.id.tv_guest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpBtnLogin() {
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.username_login);
            EditText passwordEditText = findViewById(R.id.password_login);

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            UserLoginReq req = new UserLoginReq(username, password);

            authViewModel.login(req).observe(this, response -> {
                if (response != null) {
                    saveLogin(response);
                    Toast.makeText(this, "Successfully login!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, HomeScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Error occurred during login!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
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
