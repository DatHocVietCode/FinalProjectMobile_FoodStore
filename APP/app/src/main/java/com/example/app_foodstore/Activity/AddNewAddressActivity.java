package com.example.app_foodstore.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.AddressLabelAdapter;
import com.example.app_foodstore.R;

import java.util.List;

public class AddNewAddressActivity extends AppCompatActivity {
    RecyclerView rc_keyword;
    AddressLabelAdapter adapter;
    List<String> keywordList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        AnhXa();
    }

    private void AnhXa() {
        setupRC();
    }

    private void setupRC() {
        getKeyword();
        rc_keyword = findViewById(R.id.addNewAddress_rc_label);
        adapter = new AddressLabelAdapter(this, keywordList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_keyword.setAdapter(adapter);
        rc_keyword.setLayoutManager(linearLayoutManager);
    }

    private void getKeyword() {
        keywordList = List.of("Home", "Work", "Other");
    }
}
