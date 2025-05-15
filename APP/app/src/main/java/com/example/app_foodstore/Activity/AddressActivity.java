package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.AddressAdapter;
import com.example.app_foodstore.Model.AddressModel;
import com.example.app_foodstore.R;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    RecyclerView rc_address;
    AddressAdapter adapter;
    ImageView img_addNewAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);

        AnhXa();
    }

    private void AnhXa() {
        setupRc();
        //setupBtn();
        setupAddNewAddress();
    }

    private void setupAddNewAddress() {
        img_addNewAddress = findViewById(R.id.addressScreen_imgAddNewAddress);
        img_addNewAddress.setOnClickListener(v -> {
            Intent intent = new Intent(AddressActivity.this, AddNewAddressActivity.class);
            startActivity(intent);
        });
    }


    private void setupRc() {
        List<AddressModel> listAddress;
        listAddress = getAddress();
        rc_address = findViewById(R.id.addressScreen_rc_addresses);
        adapter = new AddressAdapter(this, listAddress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_address.setLayoutManager(linearLayoutManager);
        rc_address.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<AddressModel> getAddress() {
        List<AddressModel> listAddress = new ArrayList<>();
        listAddress.add(new AddressModel(1, "Số 1 VVN, Thành phố Thủ Đức", "WORK"));
        listAddress.add(new AddressModel(2, "37/96/2, Phường 2, Quận 4, Thành Phế Hồ Chí Minh", "HOME"));
        listAddress.add(new AddressModel(3, "Ấp 5, Xã Tân Mỹ Chánh, Thành phố Mỹ Tho", "OTHER"));
        listAddress.add(new AddressModel(4, "Số 4 VVN aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "HOME"));
        listAddress.add(new AddressModel(5, "Số 5 VVN aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "WORK"));
        listAddress.add(new AddressModel(6, "Số 6 VVN aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "OTHER"));
        listAddress.add(new AddressModel(7, "Số 7 VVN aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "HOME"));
        return listAddress;
    }
}
