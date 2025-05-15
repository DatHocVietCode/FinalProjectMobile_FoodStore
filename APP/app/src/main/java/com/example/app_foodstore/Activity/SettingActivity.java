package com.example.app_foodstore.Activity;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.response.UserRes;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.UserViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    LinearLayout ln_personalInfo, ln_notifications, ln_favorite, ln_cart, ln_address
            , ln_logout;
    UserViewModel userViewModel;
    CircleImageView ms_header_avatar;
    TextView username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(R.layout.activity_setting);
        loadData();
        AnhXa();
    }
    private void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (isLoggedIn) {
            // Gọi ViewModel để lấy dữ liệu người dùng
            String token = sharedPreferences.getString("access_token", "");
            userViewModel.getUserProfile(token);

            // Quan sát dữ liệu thay đổi
            userViewModel.getUserProfileLiveData().observe(this, new Observer<UserRes>() {
                @Override
                public void onChanged(UserRes userRes) {
                    if (userRes != null) {
                        // Cập nhật UI khi dữ liệu người dùng thay đổi
                        ms_header_avatar = findViewById(R.id.activity_setting_image_profile);
                        Glide.with(SettingActivity.this)
                                .load(IMG_URL  + userRes.getProfile_image())
                                .into(ms_header_avatar);
                        TextView usernameTextView = findViewById(R.id.activity_setting_username);
                        usernameTextView.setText(userRes.getFullname());



                    } else {
                        Toast.makeText(SettingActivity.this, "Không lấy được dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.e("SettingActivity", "ms_header_avatar is null");
        }
    }

    private void AnhXa() {
        ln_personalInfo = findViewById(R.id.setting_screen_ln_personalInfo);
        ln_personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        ln_cart = findViewById(R.id.setting_screen_ln_cart);
        ln_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        ln_address = findViewById(R.id.setting_screen_ln_Adresses);
        ln_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        ln_favorite = findViewById(R.id.setting_screen_ln_favorite);
        ln_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, FavoriteFoodActivity.class);
                startActivity(intent);
            }
        });

        ln_notifications = findViewById(R.id.setting_screen_ln_notifications);
        ln_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        ln_logout = findViewById(R.id.setting_screen_ln_logout);
        ln_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Xóa toàn bộ thông tin
                editor.apply(); // Hoặc editor.commit();

                // Chuyển về LoginActivity
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại
            }
        });
    }
}
