package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.R;

public class PersonalInfoActivity extends AppCompatActivity {
    TextView tv_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        
        AnhXa();
    }

    private void AnhXa() {
        tv_edit = findViewById(R.id.PI_tv_edit);
        tv_edit.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalInfoActivity.this, EditPersonalInfoActivity.class);
            startActivity(intent);
        });
    }
}
