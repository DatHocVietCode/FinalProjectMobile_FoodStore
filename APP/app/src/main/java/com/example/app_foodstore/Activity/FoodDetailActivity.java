package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class FoodDetailActivity extends AppCompatActivity {
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
    private TextView tv_noComments;
    private static final int LOAD_COUNT = 5;
    private int currentLoaded = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == imagesList.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };
    private ImageButton btn_Favorite;
    private FoodModel foodModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_detail);
        foodModel = (FoodModel) getIntent().getSerializableExtra("foodModel");

        if (foodModel != null) {
            // Bạn có thể hiển thị dữ liệu tại đây
            Log.d("FoodDetail", "Tên món: " + foodModel.getName());
            // hoặc setText lên TextView
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
    private void loadData(){
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

        // LẤY DANH SÁCH TẤT CẢ BÌNH LUẬN TRƯỚC
        allComments = foodModel.getComments();

        if (allComments == null || allComments.isEmpty()) {
            rc_comments.setVisibility(View.GONE);
            tv_noComments.setVisibility(View.VISIBLE);
            btnLoadMore.setVisibility(View.GONE);
        } else {
            // Thiết lập adapter với danh sách rỗng ban đầu (visibleComments)
            commentAdapter = new CommentAdapter(visibleComments, FoodDetailActivity.this);
            rc_comments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rc_comments.setAdapter(commentAdapter);

            tv_noComments.setVisibility(View.GONE);
            rc_comments.setVisibility(View.VISIBLE);
            btnLoadMore.setVisibility(View.VISIBLE);

            currentLoaded = 0; // reset lại
            loadMoreComments(); // Tải những comment đầu tiên

            // Thiết lập sự kiện khi nhấn nút “Xem thêm”
            btnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMoreComments();
                }
            });
        }
    }

    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.foodDetail_cardView_bottomSheet);
        toggleButton = findViewById(R.id.foodDetail_cardView_bottomSheet_expandbtn);
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

    private void setupBtnFavorite() {
        btn_Favorite = findViewById(R.id.foodDetail_btn_favorite);
        btn_Favorite.setTag("inactive"); // Tùy vào API gọi về
        btn_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("active".equals(btn_Favorite.getTag())) {
                    btn_Favorite.setBackgroundResource(R.drawable.favorite_inactive);
                    btn_Favorite.setTag("inactive");
                } else {
                    btn_Favorite.setBackgroundResource(R.drawable.favorite_active);
                    btn_Favorite.setTag("active");
                }
            }

        });
    }

    private void setupViewPager2() {
        viewPager2 = findViewById(R.id.foodDetail_viewpager2);
        circleIndicator3 = findViewById(R.id.foodDetail_circle_indicator3);
        imagesList = foodModel.getProduct_images();
        ImageViewPager2Adapter imageViewPager2Adapter = new ImageViewPager2Adapter(imagesList);
        viewPager2.setAdapter(imageViewPager2Adapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        //viewPager2.setPageTransformer(new DepthPageTransfomer());
    }

    private void loadMoreComments() {
        allComments = foodModel.getComments();
        int nextLimit = Math.min(currentLoaded + LOAD_COUNT, allComments.size());

        for (int i = currentLoaded; i < nextLimit; i++) {
            visibleComments.add(allComments.get(i));
        }

        currentLoaded = nextLimit;
        commentAdapter.notifyDataSetChanged();
        Log.d("DEBUG", "BottomCard visibility: " + bottomSheetBehavior.getState());
        Log.d("DEBUG", "Comment count: " + visibleComments.size());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // Ẩn nút nếu đã load hết
        if (currentLoaded >= allComments.size()) {
            btnLoadMore.setVisibility(View.GONE);
        }
    }
    private void loadAllComments() {

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

//    private List<ImageModel> getImagesList() {
//        List<ImageModel> list = new ArrayList<>();
//
//        for (FoodImage img : foodModel.getImages()) {
//            list.add(new ImageModel(img.getImage_url()));
//        }
//    }
}
