package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.PaymentMethodAdapter;
import com.example.app_foodstore.Adapter.ViewPagerPaymentMethodAdapter;
import com.example.app_foodstore.Adapter.VoucherAdapter;
import com.example.app_foodstore.Model.PaymentInterfaceModel;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    boolean selected = true; // hoặc false tùy trạng thái
    ViewPager2 viewPager2;
    AppCompatSpinner spinnerVouchers;
    ImageView imageView;
    LinearLayout layout;
    ImageView checkImage;
    RecyclerView rc_methods;
    PaymentMethodAdapter paymentMethodAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        AnhXa();
    }

    private void AnhXa() {
        //setupPayment(); Sample cũ khi còn dùng linear layout
        setupVoucher();
        setupViewPager();
        setupRecyclerViewMethod();
    }

    private void setupRecyclerViewMethod() {
        rc_methods = findViewById(R.id.payment_rc_methods);
        List<PaymentInterfaceModel> paymentInterfaceModelList = GetPaymentMethod();
        paymentMethodAdapter = new PaymentMethodAdapter(this, paymentInterfaceModelList,
                new PaymentMethodAdapter.OnPaymentMethodSelectedListener() {
                    @Override
                    public void onMethodSelected(int position) {
                        // ViewPager2 nhảy đến trang tương ứng với method được chọn
                        viewPager2.setCurrentItem(position, true);
                        Log.d("Position", "onMethodSelected: " + position);
                    }
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_methods.setLayoutManager(linearLayoutManager);
        rc_methods.setAdapter(paymentMethodAdapter);
        paymentMethodAdapter.notifyDataSetChanged();
    }

    private List<PaymentInterfaceModel> GetPaymentMethod() {
        // Sau này bú từ API
        List<PaymentInterfaceModel> paymentInterfaceModelList = new ArrayList<>();
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.cash_selected, R.drawable.cash_unselected, "Cash", true));
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.momo_selected, R.drawable.momo_unselected, "Momo", false));
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.vnpay_selected, R.drawable.vnpay_unselected, "VNPay", false));
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.visa_selected, R.drawable.visa_unselected, "Visa", false));
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.master_card_selected, R.drawable.master_card_unselected, "Master Card", false));
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.paypal_selected, R.drawable.paypal_unselected, "Paypal", false));
        return paymentInterfaceModelList;
    }

    private void setupViewPager() {
        viewPager2 = findViewById(R.id.payment_viewpager2);
        ViewPagerPaymentMethodAdapter adapter = new ViewPagerPaymentMethodAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (paymentMethodAdapter != null) {
                    paymentMethodAdapter.setCheckedPosition(position);
                }
            }
        });

    }

    private void setupVoucher() {
        spinnerVouchers = findViewById(R.id.spinnerVouchers);
        List<VoucherModel> voucherList = new ArrayList<>();
        voucherList.add(new VoucherModel("Không áp dụng voucher"));
        voucherList.add(new VoucherModel("Voucher A"));
        voucherList.add(new VoucherModel("Voucher B"));
        voucherList.add(new VoucherModel("Voucher C"));

        VoucherAdapter adapter = new VoucherAdapter(this, voucherList, new VoucherAdapter.OnVoucherSelectedListener() {
            @Override
            public void onVoucherSelected(VoucherModel voucher, int position) {
                spinnerVouchers.post(() -> spinnerVouchers.setSelection(position));
                try {
                    Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                    method.setAccessible(true);
                    method.invoke(spinnerVouchers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        spinnerVouchers.setAdapter(adapter);
        // Đặt mặc định là dòng đầu tiên (không chọn voucher)
        spinnerVouchers.setSelection(0);
    }

    private void setupPayment() {
        imageView = findViewById(R.id.payment_cash_image);
        layout = findViewById(R.id.payment_cash_container);
        checkImage = findViewById(R.id.payment_cash_check);
        if (selected) {
            imageView.setImageResource(R.drawable.cash_selected);
            imageView.setBackgroundResource(R.drawable.rectangle_corner_radius_stroke_ff7622);
            checkImage.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.drawable.cash_unselected);
            imageView.setBackgroundResource(R.drawable.rectangle_corner_radius);
            checkImage.setVisibility(View.INVISIBLE);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected)
                {
                    imageView.setImageResource(R.drawable.cash_unselected);
                    imageView.setBackgroundResource(R.drawable.rectangle_corner_radius);
                    checkImage.setVisibility(View.INVISIBLE);
                    selected = false;
                }
                else
                {
                    imageView.setImageResource(R.drawable.cash_selected);
                    imageView.setBackgroundResource(R.drawable.rectangle_corner_radius_stroke_ff7622);
                    checkImage.setVisibility(View.VISIBLE);
                    selected = true;
                }
            }
        });
    }
}
