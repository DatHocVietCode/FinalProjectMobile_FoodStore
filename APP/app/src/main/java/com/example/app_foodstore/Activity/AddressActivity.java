package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.AddressAdapter;
import com.example.app_foodstore.Model.response.AddressResponse;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Utils.UserUtils;
import com.example.app_foodstore.ViewModel.AddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {

    private RecyclerView rc_address;
    private AddressAdapter adapter;
    private ImageView img_addNewAddress;

    private final List<AddressResponse> addressList = new ArrayList<>();
    private AddressViewModel addressViewModel;

    private final ActivityResultLauncher<Intent> addAddressLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadAddressFromApi();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        initViews();
        loadAddressFromApi();
    }

    private void initViews() {
        rc_address = findViewById(R.id.addressScreen_rc_addresses);
        img_addNewAddress = findViewById(R.id.addressScreen_imgAddNewAddress);

        adapter = new AddressAdapter(
                this,
                addressList,
                (addressId, position) -> {
                    String token = UserUtils.getTokenFromPreferences(this);
                    addressViewModel.deleteAddress(token, addressId).observe(this, success -> {
                        if (success != null && success) {
                            adapter.removeItem(position);
                            Toast.makeText(this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Xóa địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                },
                address -> {
                    // Trả dữ liệu địa chỉ về CartActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("selectedAddress", address); // cần AddressResponse Serializable hoặc Parcelable
                    setResult(RESULT_OK, resultIntent);
                    finish(); // đóng AddressActivity
                }
        );

        rc_address.setLayoutManager(new LinearLayoutManager(this));
        rc_address.setAdapter(adapter);

        img_addNewAddress.setOnClickListener(v -> {
            Intent intent = new Intent(AddressActivity.this, AddNewAddressActivity.class);
            addAddressLauncher.launch(intent);
        });
    }

    private void loadAddressFromApi() {
        String token = UserUtils.getTokenFromPreferences(this);
        addressViewModel.getMyAddresses(token).observe(this, responses -> {
            if (responses != null) {
                addressList.clear();
                addressList.addAll(responses);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("AddressActivity", "Failed to load addresses");
            }
        });
    }
}
