package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app_foodstore.R;
import com.example.app_foodstore.databinding.FragmentPaymentCashBinding;

public class Fragment_payment_cash extends Fragment {
    FragmentPaymentCashBinding binding;
    private RadioGroup paymentMethodGroup;
    private RadioButton paymentCashOnDelivery;
    private RadioButton paymentCashOnRestaurant;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaymentCashBinding.inflate(inflater, container, false);

        // Xử lý sự kiện khi người dùng chọn một phương thức thanh toán
        /*binding.paymentCashRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.payment_cash_on_delivery) {
                    // Khi chọn "Cash on Delivery"
                    Toast.makeText(getActivity(), "Cash on Delivery (COD) selected", Toast.LENGTH_SHORT).show();
                    // Xử lý thêm ở đây nếu cần (Ví dụ, cập nhật dữ liệu, UI,...)
                } else if (checkedId == R.id.payment_cash_on_restaurant) {
                    // Khi chọn "Pay at Restaurant"
                    Toast.makeText(getActivity(), "Pay at Restaurant selected", Toast.LENGTH_SHORT).show();
                    // Xử lý thêm ở đây nếu cần
                } else {
                    // Trường hợp mặc định (nếu có)
                }
            }
        });*/
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }
}
