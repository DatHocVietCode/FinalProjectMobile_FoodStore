package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.AddressLabelAdapter;
import com.example.app_foodstore.Model.request.AddressRequest;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Utils.UserUtils;
import com.example.app_foodstore.ViewModel.AddressViewModel;

import java.util.List;

public class AddNewAddressActivity extends AppCompatActivity implements AddressLabelAdapter.OnLabelSelectedListener {

    private RecyclerView rcKeyword;
    private AddressLabelAdapter adapter;
    private List<String> keywordList;
    private String selectedLabel = "Home";  // Mặc định là Home

    private EditText edtAddress;
    private Switch switchDefault;
    private Button btnSave;

    private AddressViewModel addressViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        edtAddress = findViewById(R.id.addAddressScreen_et_Address);
        switchDefault = findViewById(R.id.switch_default_address);
        btnSave = findViewById(R.id.addNewAddress_btn_addAddress);
        rcKeyword = findViewById(R.id.addNewAddress_rc_label);
    }

    private void setupRecyclerView() {
        keywordList = List.of("Home", "Work", "Other");
        adapter = new AddressLabelAdapter(this, keywordList, this);
        rcKeyword.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcKeyword.setAdapter(adapter);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> {
            String addressText = edtAddress.getText().toString().trim();
            boolean isDefault = switchDefault.isChecked();

            if (addressText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                return;
            }

            AddressRequest request = new AddressRequest(addressText, selectedLabel.trim(), isDefault);

            String token = UserUtils.getTokenFromPreferences(this); // Lấy token người dùng

            addressViewModel.addAddress(token, request).observe(this, success -> {
                if (success != null && success) {
                    Toast.makeText(this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // Báo kết quả về AddressActivity
                    finish();
                } else {
                    Toast.makeText(this, "Thêm địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onLabelSelected(String label) {
        selectedLabel = label;

        if ("Home".equalsIgnoreCase(label)) {
            switchDefault.setChecked(true);
        } else {
            switchDefault.setChecked(false);
        }
    }
}
