package com.example.app_foodstore.Activity;

import static com.example.app_foodstore.ZaloPay.Constant.AppInfo.APP_ID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.PaymentMethodAdapter;
import com.example.app_foodstore.Adapter.ViewPagerPaymentMethodAdapter;
import com.example.app_foodstore.Adapter.VoucherSpinnerAdapter;
import com.example.app_foodstore.Model.OrderDetailModel;
import com.example.app_foodstore.Model.PaymentInterfaceModel;
import com.example.app_foodstore.Model.VoucherModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ZaloPay.Api.CreateOrder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
    boolean selected = true; // hoặc false tùy trạng thái
    ViewPager2 viewPager2;
    AppCompatSpinner spinnerVouchers;
    ImageView imageView;
    LinearLayout layout;
    ImageView checkImage;
    RecyclerView rc_methods;
    PaymentMethodAdapter paymentMethodAdapter;
    Button btn_pay;
    List<OrderDetailModel> orderDetailModelList;
    private BottomSheetBehavior<CardView> bottomSheetBehavior;
    private ImageButton toggleButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ZaloPaySDK.init(APP_ID, Environment.SANDBOX);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        AnhXa();
    }
    //Cần bắt sự kiện OnNewIntent vì ZaloPay App sẽ gọi deeplink về app của Merchant
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    //Implement interface PayOrderListener để nhận kết quả thanh toán
    private static class MyZaloPayListener implements PayOrderListener {
        @Override
        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
            //Handle Success
        }

        @Override
        public void onPaymentCanceled(String zpTransToken, String appTransID) {
            //Handle User Canceled
        }

        @Override
        public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
            //Redirect to Zalo/ZaloPay Store when zaloPayError == ZaloPayError.PAYMENT_APP_NOT_FOUND
            //Handle Error
        }
    }
    private void AnhXa() {
        //setupPayment(); Sample cũ khi còn dùng linear layout
        setupVoucher();
        setupViewPager();
        setupRecyclerViewMethod();
        setupbtnPay();
        //setupOrderDetail();
        //setupBottomCard();
    }
    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.payment_cardView_bottomSheet);
        toggleButton = findViewById(R.id.payment_cardView_bottomSheet_expandbtn);
        // Khởi tạo behavior từ CardView
        bottomSheetBehavior = BottomSheetBehavior.from(bottomCard);
        // Trạng thái ban đầu (ẩn hoặc collapsed)
        bottomCard.post(() -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setHideable(false);
            int peekDp = 30; // dp bạn muốn
            float density = getResources().getDisplayMetrics().density;
            int peekPx = (int) (peekDp * density);
            bottomSheetBehavior.setPeekHeight(peekPx);
        });

        Log.d("STate", "Current: " + bottomSheetBehavior.getState());
        Log.d("STate", "Peek height: " + bottomSheetBehavior.getPeekHeight());
        Log.d("STate", "Is Hideable: " + bottomSheetBehavior.isHideable());

        // Xử lý toggle khi nhấn nút
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("STate", "onClick: " + bottomSheetBehavior.getState());

                int currentState = bottomSheetBehavior.getState();

                // Kiểm tra xem BottomSheet có đang trong trạng thái Settling không
                if (currentState != BottomSheetBehavior.STATE_SETTLING) {
                    if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        //toggleButton.setImageResource(R.drawable.expand_up);
                        bottomSheetBehavior.setHideable(false);
                        int peekDp = 30; // dp bạn muốn
                        float density = getResources().getDisplayMetrics().density;
                        int peekPx = (int) (peekDp * density);
                        bottomSheetBehavior.setPeekHeight(peekPx);
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        //toggleButton.setImageResource(R.drawable.expand_down);
                    }
                } else {
                    Log.d("STate", "BottomSheet is settling, cannot change state");
                }
            }
        });

    }
    /*private void setupOrderDetail() {
        rc_orderDetail = findViewById(R.id.payment_rc_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_orderDetail.setLayoutManager(linearLayoutManager);
        orderDetailModelList = getOrderDetail();
        OrderDetailAdapter adapter = new OrderDetailAdapter(this, orderDetailModelList);
        rc_orderDetail.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }*/

    private List<OrderDetailModel> getOrderDetail() {
        List<OrderDetailModel> orderDetailModelList = new ArrayList<>();
        orderDetailModelList.add(new OrderDetailModel(1, "FoodNamedasdadasadadasddasdsad", 1));
        orderDetailModelList.add(new OrderDetailModel(2, "Món ăn B", 2));
        orderDetailModelList.add(new OrderDetailModel(3, "Món ăn C", 3));
        orderDetailModelList.add(new OrderDetailModel(1, "Món ăn A", 1));
        orderDetailModelList.add(new OrderDetailModel(2, "Món ăn B", 2));
        orderDetailModelList.add(new OrderDetailModel(3, "Món ăn C", 3));
        orderDetailModelList.add(new OrderDetailModel(1, "Món ăn A", 1));
        orderDetailModelList.add(new OrderDetailModel(2, "Món ăn B", 2));
        orderDetailModelList.add(new OrderDetailModel(3, "Món ăn C", 3));
        orderDetailModelList.add(new OrderDetailModel(1, "Món ăn A", 1));
        orderDetailModelList.add(new OrderDetailModel(2, "Món ăn B", 2));
        orderDetailModelList.add(new OrderDetailModel(3, "Món ăn C", 3));

        return orderDetailModelList;
    }

    private void setupbtnPay() {
        btn_pay = findViewById(R.id.payment_btn_payAndConfirm);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() != 1)
                    return;
                CreateOrder orderApi = new CreateOrder();

                try {
                    JSONObject data = orderApi.createOrder("100000");
                    //Log.d("Amount", txtAmount.getText().toString());
                    String code = data.getString("return_code");
                    Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        //IsDone();
                        ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://payment", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(PaymentActivity.this)
                                                .setTitle("Payment Success")
                                                .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .setNegativeButton("Cancel", null).show();
                                    }

                                });
                                //IsLoading();
                            }

                            @Override
                            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                                new AlertDialog.Builder(PaymentActivity.this)
                                        .setTitle("User Cancel Payment")
                                        .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setNegativeButton("Cancel", null).show();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                                new AlertDialog.Builder(PaymentActivity.this)
                                        .setTitle("Payment Fail")
                                        .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setNegativeButton("Cancel", null).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }


    private void setupVoucher() {
        spinnerVouchers = findViewById(R.id.spinnerVouchers);
        List<VoucherModel> voucherList = new ArrayList<>();
//        voucherList.add(new VoucherModel("Không áp dụng voucher"));
//        voucherList.add(new VoucherModel("Voucher A"));
//        voucherList.add(new VoucherModel("Voucher B"));
//        voucherList.add(new VoucherModel("Voucher C"));

        VoucherSpinnerAdapter adapter = new VoucherSpinnerAdapter(this, voucherList, new VoucherSpinnerAdapter.OnVoucherSelectedListener() {
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
            imageView.setBackgroundResource(R.drawable.rectangle_corner_radius_bg_f6f6f6);
            checkImage.setVisibility(View.INVISIBLE);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected)
                {
                    imageView.setImageResource(R.drawable.cash_unselected);
                    imageView.setBackgroundResource(R.drawable.rectangle_corner_radius_bg_f6f6f6);
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
