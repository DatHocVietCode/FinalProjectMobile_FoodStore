package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.R;

public class SettingActivity extends AppCompatActivity {
    LinearLayout ln_personalInfo, ln_notifications, ln_favorite;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        AnhXa();
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
    }
}
