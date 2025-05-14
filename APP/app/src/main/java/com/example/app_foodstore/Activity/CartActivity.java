package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.ItemCartAdapter;
import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView rc_cart;
    ItemCartAdapter cartAdapter;
    TextView tv_edit, tv_done;
    private BottomSheetBehavior<CardView> bottomSheetBehavior;
    private ImageButton toggleButton;
    Button btn_placeOrder;
    TextView tv_breakdown;
    List<CartModel> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        AnhXa();
    }

    private void AnhXa() {
        getCartItems();
        setupRcCart();
        setupBottomCard();
        setupBtn();
        setupPlaceOrder();
    }

    private void getCartItems() {
        // Lấy từ API
        list = new ArrayList<>();
        list.add(new CartModel(1));
        list.add(new CartModel(2));
        list.add(new CartModel(3));
        list.add(new CartModel(4));
        list.add(new CartModel(5));
        list.add(new CartModel(6));
        list.add(new CartModel(7));
        list.add(new CartModel(8));
        list.add(new CartModel(9));
        list.add(new CartModel(10));
    }

    private void setupPlaceOrder() {
        btn_placeOrder = findViewById(R.id.cart_btn_placeOrder);
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    private void setupBtn() {
        tv_edit = findViewById(R.id.cart_tv_edit);
        tv_done = findViewById(R.id.cart_tv_done);

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_edit.setVisibility(View.GONE);
                tv_done.setVisibility(View.VISIBLE);
                cartAdapter.setEditMode(true); // bật nút xóa
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_edit.setVisibility(View.VISIBLE);
                tv_done.setVisibility(View.GONE);
                cartAdapter.setEditMode(false); // tắt nút xóa
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.cart_cardView_bottomSheet);
        toggleButton = findViewById(R.id.cart_cardView_bottomSheet_expandbtn);
        tv_breakdown = findViewById(R.id.cart_btn_breakDown);
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

        tv_breakdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, BreakDownOrderActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupRcCart() {
        rc_cart = findViewById(R.id.cart_rc_items);
        cartAdapter = new ItemCartAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_cart.setLayoutManager(linearLayoutManager);
        rc_cart.setAdapter(cartAdapter);
    }
}
