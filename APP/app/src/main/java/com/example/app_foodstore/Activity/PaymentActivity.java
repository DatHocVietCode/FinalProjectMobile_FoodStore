package com.example.app_foodstore.Activity;

import static com.example.app_foodstore.ZaloPay.Constant.AppInfo.APP_ID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.PaymentMethodAdapter;
import com.example.app_foodstore.Adapter.ViewPagerPaymentMethodAdapter;
import com.example.app_foodstore.Adapter.VoucherSpinnerAdapter;
import com.example.app_foodstore.Interface.PaymentCallBack;
import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.Model.PaymentInterfaceModel;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.Model.request.PaymentRequest;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.PaymentViewModel;
import com.example.app_foodstore.ViewModel.VoucherViewModel;
import com.example.app_foodstore.ZaloPay.Api.CreateOrder;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity implements PaymentCallBack {

    private ViewPager2 viewPager2;
    private VoucherViewModel voucherViewModel;
    private AppCompatSpinner spinnerVouchers;
    private RecyclerView rc_methods;
    private PaymentMethodAdapter paymentMethodAdapter;
    private Button btn_pay;
    private ImageButton toggleButton;
    private String token;
    private PaymentViewModel paymentViewModel;
    private Long idAddress;
    private RadioGroup radioGroupPrice;
    double totalAmount;
    String order,delivery,discount;
    TextView tvDeliveryFee,tvVoucher,tvOrderPrice;
    PaymentRequest paymentRequest;
    String paymentMethod, shippingMethodName;
    Long idVoucher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ZaloPaySDK.init(APP_ID, Environment.SANDBOX);
        token = UserUtils.getTokenFromPreferences(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        AnhXa();
    }

    // Deeplink callback from ZaloPay App
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ZaloPay", "onNewIntent triggered");
        Log.d("ZaloPay", "Intent Data: " + intent.getDataString());
        if (intent != null && intent.getData() != null) {
            Log.d("ZaloPay", "Deeplink data: " + intent.getData().toString());
        } else {
            Log.d("ZaloPay", "No Deeplink Data Received");
        }
        ZaloPaySDK.getInstance().onResult(intent);
    }

    // Listener for ZaloPay payment result
    private static class MyZaloPayListener implements PayOrderListener {
        private final PaymentActivity activity;
        private final PaymentCallBack callback;
        public MyZaloPayListener(PaymentActivity activity, PaymentCallBack paymentCallback) {
            this.activity = activity;
            this.callback = paymentCallback;
        }

        @Override
        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
            activity.runOnUiThread(() -> {
              /*  Log.d("ZaloPay", "Onpayment Success Triggered");
                Intent intent = new Intent(activity, PaymentNotificationActivity.class);
                activity.startActivity(intent);*/
                callback.onPaymentSuccess();
            });
        }

        @Override
        public void onPaymentCanceled(String zpTransToken, String appTransID) {
            activity.runOnUiThread(() -> {
                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle("User Canceled Payment")
                        .setMessage(String.format("Transaction Token: %s", zpTransToken))
                        .setPositiveButton("OK", null)
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF7622"));
            });
        }

        @Override
        public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
            activity.runOnUiThread(() -> {
                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle("ZaloPay Payment Failed")
                        .setMessage(String.format("Error: %s\nTransaction Token: %s", zaloPayError.toString(), zpTransToken))
                        .setPositiveButton("OK", null)
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF7622"));
            });
        }
    }

    private void AnhXa() {
        getArguments();
        initViewModel();
        setupRadioTransport();
        setupTextView();
        setupVoucher();
        setupViewPager();
        setupRecyclerViewMethod();
        setupbtnPay();
    }

    private void initPayment() {
        // 🔸 Tạo PaymentRequest
        Log.d("Payment", "initPayment: " + idAddress + paymentMethod + shippingMethodName + idVoucher);
        paymentRequest = new PaymentRequest(paymentMethod, shippingMethodName, idVoucher, idAddress);
    }

    private void setupRadioTransport() {
        radioGroupPrice = findViewById(R.id.radioGroupTypeOfTransport);
        delivery = "10000";
        radioGroupPrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioPriceStandard)
                {
                    delivery = "10000";
                    tvDeliveryFee.setText(delivery + "VND");
                }
                else
                {
                    delivery = "20000";
                    tvDeliveryFee.setText(delivery + "VND");
                }
            }
        });
    }

    private void initViewModel() {
        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
    }

    private void getArguments() {
        // Lấy idAddress từ Intent
        idAddress = getIntent().getLongExtra("idAddress", -1L);
        order = String.valueOf(getIntent().getDoubleExtra("order", 0.0));

        if (idAddress != -1L) {
            Log.d("PaymentActivity", "idAddress: " + idAddress);
        } else {
            Log.d("PaymentActivity", "idAddress không được truyền hoặc có giá trị không hợp lệ");
        }
    }

    private void setupTextView() {
        tvDeliveryFee = findViewById(R.id.payment_ordersummary_DeliveryFee);
        tvVoucher = findViewById(R.id.payment_ordersummary_voucher);
        tvOrderPrice = findViewById(R.id.payment_ordersummary_order_price);

        tvDeliveryFee.setText(delivery);
        tvVoucher.setText(discount);
        tvOrderPrice.setText(order);
    }

    private void setupbtnPay() {
        btn_pay = findViewById(R.id.payment_btn_payAndConfirm);
        btn_pay.setOnClickListener(v -> {
            // Lấy phương thức thanh toán đang chọn (tương ứng với ViewPager2)
            int currentMethodPosition = viewPager2.getCurrentItem();
            int currentShippingMethodId = radioGroupPrice.getCheckedRadioButtonId();
            // 🔸 Lấy shippingMethod từ RadioGroup
            int selectedShippingId = radioGroupPrice.getCheckedRadioButtonId();
            if (selectedShippingId == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức vận chuyển!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentShippingMethodId == R.id.radioPriceStandard)
            {
                shippingMethodName = "Standard";
            }
            else
            {
                shippingMethodName = "Express";
            }
            /*RadioButton selectedShippingButton = findViewById(selectedShippingId);
            shippingMethodName = selectedShippingButton.getText().toString();
            String[] parts = shippingMethodName.split(" \\("); // Tách lấy tên
            //String shippingMethodName = parts[0]; // Ví dụ: "Standard" hoặc "Express"*/

            // 🔸 Lấy voucher đang chọn từ spinner
            VoucherModel selectedVoucher = (VoucherModel) spinnerVouchers.getSelectedItem();

            idVoucher = selectedVoucher != null ? selectedVoucher.getId() : null;
            if(selectedVoucher == null){
                discount = "0";
            }
            else{
                discount = selectedVoucher.getDiscount().toString();
            }
            Log.d("Payment", "setupbtnPay: " + order);
            double orderValue = Double.parseDouble(order);
            double discountValue = Double.parseDouble(discount);
            double deliveryValue = Double.parseDouble(delivery);

            // Tính tổng
            totalAmount = orderValue - discountValue + deliveryValue;
            paymentMethod = null;
            switch (currentMethodPosition) {
                case 0:
                    paymentMethod = "Cash";
                    initPayment();
                    callPayment();
                    break;
                case 1:
                    paymentMethod = "ZaloPay";
                    CreateOrder orderApi = new CreateOrder();
                    try {
                        String money = String.valueOf((int) totalAmount);
                        JSONObject data = orderApi.createOrder(money);
                        String code = data.getString("return_code");
                        Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                        if (code.equals("1")) {
                            String token = data.getString("zp_trans_token");

                            // Khởi tạo listener với context của Activity
                            MyZaloPayListener listener = new MyZaloPayListener(PaymentActivity.this, this);

                            // Thanh toán ZaloPay
                            ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://payment", listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2: paymentMethod = "VNPay"; break;
                case 3: paymentMethod = "Visa"; break;
                case 4: paymentMethod = "MasterCard"; break;
                case 5: paymentMethod = "Paypal"; break;
            }

        });

    }
    @Override
    public void onPaymentSuccess() {
        initPayment();
        callPayment();
    }
    private void callPayment()
    {
        if (paymentRequest != null) {
            paymentViewModel.makePayment(token, paymentRequest).observe(PaymentActivity.this, isSuccess -> {
                if (isSuccess) {
                    //Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                    // Xóa toàn bộ stack và chuyển về HomeScreen
                    Intent homeIntent = new Intent(PaymentActivity.this, HomeScreenActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);

                    // Sau đó gọi TrackOrder
                    Intent notificationPayment = new Intent(PaymentActivity.this, PaymentNotificationActivity.class);
                    startActivity(notificationPayment);

                    // Kết thúc PaymentActivity
                    finish();
                } else {
                    Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Payment Request has not been initialized!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setupRecyclerViewMethod() {
        rc_methods = findViewById(R.id.payment_rc_methods);
        List<PaymentInterfaceModel> paymentInterfaceModelList = GetPaymentMethod();
        paymentMethodAdapter = new PaymentMethodAdapter(this, paymentInterfaceModelList,
                position -> {
                    viewPager2.setCurrentItem(position, true);
                    Log.d("Position", "onMethodSelected: " + position);
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_methods.setLayoutManager(linearLayoutManager);
        rc_methods.setAdapter(paymentMethodAdapter);
        paymentMethodAdapter.notifyDataSetChanged();
    }

    private List<PaymentInterfaceModel> GetPaymentMethod() {
        List<PaymentInterfaceModel> paymentInterfaceModelList = new ArrayList<>();
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.cash_selected, R.drawable.cash_unselected, "Cash", true));
        paymentInterfaceModelList.add(new PaymentInterfaceModel(R.drawable.zalopay_selected, R.drawable.zalopay_unselected, "ZaloPay", false));
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
        VoucherViewModel voucherViewModel = new ViewModelProvider(this).get(VoucherViewModel.class);

        voucherViewModel.getMyVouchers(token).observe(this, voucherModels -> {
            if (voucherModels != null && !voucherModels.isEmpty()) {
                VoucherSpinnerAdapter adapter = new VoucherSpinnerAdapter(this, R.layout.spinner_voucher_item, voucherModels);
                spinnerVouchers.setAdapter(adapter);
            } else {
                // Xử lý không có voucher
                spinnerVouchers.setAdapter(null);

            }
        });

    }
}
