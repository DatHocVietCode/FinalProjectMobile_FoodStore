package com.example.app_foodstore.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.CommentAdapter;
import com.example.app_foodstore.Adapter.ImageViewPager2Adapter;
import com.example.app_foodstore.Model.CommentModel;
import com.example.app_foodstore.Model.FoodImage;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Model.ImageModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Transformer.ZoomOutPageTransformer;
import com.example.app_foodstore.ViewModel.CartViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class FoodDetailActivity extends AppCompatActivity {
    private CartViewModel cartViewModel;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<FoodImage> imagesList;
    private Handler handler = new Handler();
    private BottomSheetBehavior<CardView> bottomSheetBehavior;
    private ImageButton toggleButton;
    private RecyclerView rc_comments;
    private CommentAdapter commentAdapter;
    private List<CommentModel> allComments;
    private List<CommentModel> visibleComments;
    private Button btnLoadMore;
    private TextView tv_noComments, tv_count, tv_price;
    private static final int LOAD_COUNT = 5;
    private int currentLoaded = 0;
    private Runnable runnable = () -> {
        if (viewPager2.getCurrentItem() == imagesList.size() - 1) {
            viewPager2.setCurrentItem(0);
        } else {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    private ImageButton btn_Favorite;
    private FoodModel foodModel;

    Button btn_addCart;
    ImageButton btn_add, btn_minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_detail);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        foodModel = (FoodModel) getIntent().getSerializableExtra("foodModel");

        if (foodModel != null) {
            Log.d("FoodDetail", "Tên món: " + foodModel.getName());
        }
        AnhXa();
    }

    private void AnhXa() {
        setupViewPager2();
        setupBtnFavorite();
        setupBottomCard();
        loadData();
        loadAllComments();
        setupComments();
    }

    private void loadData() {
        TextView tv_foodName = findViewById(R.id.foodDetail_tv_name);
        tv_foodName.setText(foodModel.getName());

        TextView tv_foodDescription = findViewById(R.id.foodDetail_tv_decs);
        tv_foodDescription.setText(foodModel.getDescription());

        View includeLayout = findViewById(R.id.foodDetail_include_rating_delivery_mins);
        TextView tvRating = includeLayout.findViewById(R.id.fragment_food_rating_tv_rating);
        tvRating.setText(foodModel.getAverage_rating().toString());
    }

    private void setupComments() {
        visibleComments = new ArrayList<>();
        btnLoadMore = findViewById(R.id.foodDetail_btn_moreComments);
        rc_comments = findViewById(R.id.foodDetail_rc_Comments);
        tv_noComments = findViewById(R.id.foodDetail_tv_noComments);

        allComments = foodModel.getComments();

        if (allComments == null || allComments.isEmpty()) {
            rc_comments.setVisibility(View.GONE);
            tv_noComments.setVisibility(View.VISIBLE);
            btnLoadMore.setVisibility(View.GONE);
        } else {
            commentAdapter = new CommentAdapter(visibleComments, this);
            rc_comments.setLayoutManager(new LinearLayoutManager(this));
            rc_comments.setAdapter(commentAdapter);

            tv_noComments.setVisibility(View.GONE);
            rc_comments.setVisibility(View.VISIBLE);
            btnLoadMore.setVisibility(View.VISIBLE);

            currentLoaded = 0;
            loadMoreComments();

            btnLoadMore.setOnClickListener(v -> loadMoreComments());
        }
    }

    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.foodDetail_cardView_bottomSheet);
        toggleButton = findViewById(R.id.foodDetail_cardView_bottomSheet_expandbtn);
        btn_add = findViewById(R.id.foodDetail_btn_plus);
        btn_minus = findViewById(R.id.foodDetail_btn_minus);
        tv_count = findViewById(R.id.foodDetail_tv_count);
        btn_addCart = findViewById(R.id.foodDetail_btn_addToCart);
        tv_price = findViewById(R.id.foodDetail_tv_total_price);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomCard);
        bottomCard.post(() -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setHideable(false);
            bottomSheetBehavior.setPeekHeight((int) (30 * getResources().getDisplayMetrics().density));
        });

        toggleButton.setOnClickListener(view -> {
            int state = bottomSheetBehavior.getState();
            if (state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else if (state == BottomSheetBehavior.STATE_COLLAPSED ||
                    state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        int[] count = {1};
        tv_count.setText(String.valueOf(count[0]));

        float pricePerItem = foodModel.getPrice();
        tv_price.setText(String.valueOf(pricePerItem * count[0]));

        btn_add.setOnClickListener(view -> {
            count[0]++;
            tv_count.setText(String.valueOf(count[0]));
            tv_price.setText(String.valueOf(pricePerItem * count[0]));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        btn_minus.setOnClickListener(view -> {
            if (count[0] > 1) {
                count[0]--;
                tv_count.setText(String.valueOf(count[0]));
                tv_price.setText(String.valueOf(pricePerItem * count[0]));
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        btn_addCart.setOnClickListener(view -> addToCart());
    }

    private void addToCart() {
        int quantity = Integer.parseInt(tv_count.getText().toString());
        Long productId = foodModel.getId();
        String token = UserUtils.getTokenFromPreferences(this);

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Bạn cần đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        cartViewModel.addCartItem(token, productId, quantity).observe(this, success -> {
            Toast.makeText(this, Boolean.TRUE.equals(success) ?
                    "Đã thêm vào giỏ hàng" : "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupBtnFavorite() {
        btn_Favorite = findViewById(R.id.foodDetail_btn_favorite);
        btn_Favorite.setTag("inactive");
        btn_Favorite.setOnClickListener(view -> {
            if ("active".equals(btn_Favorite.getTag())) {
                btn_Favorite.setBackgroundResource(R.drawable.favorite_inactive);
                btn_Favorite.setTag("inactive");
            } else {
                btn_Favorite.setBackgroundResource(R.drawable.favorite_active);
                btn_Favorite.setTag("active");
            }
        });
    }

    private void setupViewPager2() {
        viewPager2 = findViewById(R.id.foodDetail_viewpager2);
        circleIndicator3 = findViewById(R.id.foodDetail_circle_indicator3);
        imagesList = foodModel.getProduct_images();

        ImageViewPager2Adapter adapter = new ImageViewPager2Adapter(imagesList);
        viewPager2.setAdapter(adapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }

    private void loadMoreComments() {
        int nextLimit = Math.min(currentLoaded + LOAD_COUNT, allComments.size());
        for (int i = currentLoaded; i < nextLimit; i++) {
            visibleComments.add(allComments.get(i));
        }
        currentLoaded = nextLimit;
        commentAdapter.notifyDataSetChanged();

        if (currentLoaded >= allComments.size()) {
            btnLoadMore.setVisibility(View.GONE);
        }
    }

    private void loadAllComments() {
        // Hiện tại chưa dùng
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }
}
