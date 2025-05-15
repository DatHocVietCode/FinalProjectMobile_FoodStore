package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.ItemCartAdapter;
import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Activity.UserUtils;
import com.example.app_foodstore.ViewModel.CartViewModel;
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
    List<CartModel> list = new ArrayList<>();
    CartViewModel cartViewModel;
    TextView tv_totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        AnhXa();
        setupViewModel();
        setupRcCart();
        loadCartFromApi();
    }

    private void AnhXa() {
        setupBottomCard();
        setupBtn();
        setupPlaceOrder();
    }

    private void setupViewModel() {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void loadCartFromApi() {
        String userToken = UserUtils.getTokenFromPreferences(this);
        cartViewModel.getMyCart(userToken).observe(this, cartItems -> {
            if (cartItems != null) {
                list.clear();
                list.addAll(cartItems);
                cartAdapter.updateCartList(list);
                updateTotalPrice(list);
            } else {
                Log.e("CartActivity", "Cart items null");
            }
        });
    }
    private void updateTotalPrice(List<CartModel> cartItems) {
        double total = 0.0;
        for (CartModel item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        String formatted = String.format("%,.0f", total);
        tv_totalPrice.setText(formatted + "đ");
    }

    private void setupPlaceOrder() {
        tv_totalPrice = findViewById(R.id.cart_tv_total_price);
        btn_placeOrder = findViewById(R.id.cart_btn_placeOrder);
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        btn_placeOrder.setOnClickListener(view -> startActivity(intent));
    }

    private void setupBtn() {
        tv_edit = findViewById(R.id.cart_tv_edit);
        tv_done = findViewById(R.id.cart_tv_done);

        tv_edit.setOnClickListener(view -> {
            tv_edit.setVisibility(View.GONE);
            tv_done.setVisibility(View.VISIBLE);
            cartAdapter.setEditMode(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        tv_done.setOnClickListener(view -> {
            tv_edit.setVisibility(View.VISIBLE);
            tv_done.setVisibility(View.GONE);
            cartAdapter.setEditMode(false);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }

    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.cart_cardView_bottomSheet);
        toggleButton = findViewById(R.id.cart_cardView_bottomSheet_expandbtn);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomCard);

        bottomCard.post(() -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setHideable(false);
            int peekDp = 30;
            float density = getResources().getDisplayMetrics().density;
            int peekPx = (int) (peekDp * density);
            bottomSheetBehavior.setPeekHeight(peekPx);
        });

        toggleButton.setOnClickListener(view -> {
            int currentState = bottomSheetBehavior.getState();
            if (currentState != BottomSheetBehavior.STATE_SETTLING) {
                if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

    private void setupRcCart()  {
        rc_cart = findViewById(R.id.cart_rc_items);

        cartAdapter = new ItemCartAdapter(this, list, new ItemCartAdapter.OnCartItemActionListener() {
            @Override
            public void onQuantityChanged(CartModel cartItem, int newQuantity) {
                String token = UserUtils.getTokenFromPreferences(CartActivity.this);
                cartViewModel.updateCartItem(token, cartItem.getProductId(), newQuantity).observe(CartActivity.this, success -> {
                    if (success) {
                        cartItem.setQuantity(newQuantity); // Cập nhật local
                        cartAdapter.notifyDataSetChanged(); // Refresh RecyclerView
                        updateTotalPrice(list); // Cập nhật tổng
                    } else {
                        Toast.makeText(CartActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onDeleteItem(CartModel cartItem) {
                String token = UserUtils.getTokenFromPreferences(CartActivity.this);
                cartViewModel.deleteCartItem(token, cartItem.getProductId()).observe(CartActivity.this, success -> {
                    if (success) {
                        list.remove(cartItem);
                        cartAdapter.updateCartList(list);
                        updateTotalPrice(list);
                        Toast.makeText(CartActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CartActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, new ItemCartAdapter.OnItemChangeListener() {
            @Override
            public void onItemChanged() {
                if (bottomSheetBehavior != null) {
                    // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        rc_cart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rc_cart.setAdapter(cartAdapter);
    }
}
