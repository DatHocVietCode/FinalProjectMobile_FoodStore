package com.example.app_foodstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.CommentAdapter;
import com.example.app_foodstore.Adapter.ImageViewPager2Adapter;
import com.example.app_foodstore.Model.CommentModel;
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
    private List<ImageModel> imagesList;
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
    ImageButton btn_add, btn_minus;
    TextView tv_count, tv_price;
    Button btn_addCart;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_detail);
        AnhXa();
    }

    private void AnhXa() {
        setupViewPager2();
        setupBtnFavorite();
        setupBottomCard();
        loadAllComments();
        setupComments();
    }

    private void setupComments() {
        visibleComments = new ArrayList<>();
        btnLoadMore = findViewById(R.id.foodDetail_btn_moreComments);
        rc_comments = findViewById(R.id.foodDetail_rc_Comments);
        if (allComments.isEmpty()) {
            rc_comments.setVisibility(View.GONE);
            tv_noComments = findViewById(R.id.foodDetail_tv_noComments);
            tv_noComments.setVisibility(View.VISIBLE);
            btnLoadMore.setVisibility(View.GONE);
        } else {
            commentAdapter = new CommentAdapter(visibleComments, FoodDetailActivity.this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(FoodDetailActivity.this, LinearLayoutManager.VERTICAL, false);
            rc_comments.setAdapter(commentAdapter);
            rc_comments.setLayoutManager(layoutManager);
            commentAdapter.notifyDataSetChanged();
        }
        loadMoreComments(); // load 5 bình luận đầu tiên

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreComments();
            }
        });
    }

    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.foodDetail_cardView_bottomSheet);
        toggleButton = findViewById(R.id.foodDetail_cardView_bottomSheet_expandbtn);
        btn_add = findViewById(R.id.foodDetail_btn_plus);
        btn_minus = findViewById(R.id.foodDetail_btn_minus);
        tv_count = findViewById(R.id.foodDetail_tv_count);
        btn_addCart = findViewById(R.id.foodDetail_btn_addToCart);
        tv_price = findViewById(R.id.foodDetail_tv_total_price);
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
        // Khởi tạo giá trị ban đầu
        int[] count = {2}; // Đặt trong mảng để có thể thay đổi giá trị bên trong Lambda

        tv_count.setText(String.valueOf(count[0]));

        // Xử lý sự kiện khi bấm nút "+"
        btn_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                // Lấy giá trị hiện tại của TextView và chuyển thành số
                int count = Integer.parseInt(tv_count.getText().toString());

                // Tăng giá trị
                count++;

                // Lấy trạng thái hiện tại của BottomSheet trước khi thay đổi
                int currentState = bottomSheetBehavior.getState();

                // Cập nhật lại TextView
                tv_count.setText(String.valueOf(count));
                tv_price.setText(String.valueOf(count * 100)); // Chỉnh lại logic
                // Nếu cần, thiết lập lại BottomSheetBehavior với trạng thái cũ
                bottomSheetBehavior.setState(currentState);

                // Debug log để kiểm tra
                Log.d("DEBUG", "Current BottomSheet State: " + currentState);
            }
        });

        // Xử lý sự kiện khi bấm nút "-"
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                // Lấy giá trị hiện tại của TextView và chuyển thành số
                int count = Integer.parseInt(tv_count.getText().toString());

                // Giảm giá trị nếu count > 1
                if (count > 1) {
                    count--;
                }

                // Lấy trạng thái hiện tại của BottomSheet trước khi thay đổi
                int currentState = bottomSheetBehavior.getState();

                // Cập nhật lại TextView
                tv_count.setText(String.valueOf(count));
                tv_price.setText(String.valueOf(count * 100)); // Chỉnh lại logic
                // Nếu cần, thiết lập lại BottomSheetBehavior với trạng thái cũ
                bottomSheetBehavior.setState(currentState);

                // Debug log để kiểm tra
                Log.d("DEBUG", "Current BottomSheet State: " + currentState);
            }
        });


        btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "onClick: " + bottomSheetBehavior.getState());
                Toast.makeText(view.getContext(), "Success", Toast.LENGTH_SHORT).show();  // Sửa tại đây
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
        imagesList = getImagesList();
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
        allComments = new ArrayList<>();
        // Tạo một vài bình luận giả
        allComments.add(new CommentModel(1, R.drawable.baseline_account_circle_24, "Nguyen Van A",
                "Món ăn ngon, tôi rất thích!", 4.5, new Date()));
        allComments.add(new CommentModel(2, R.drawable.baseline_account_circle_24, "Le Thi B",
                "Món ăn khá ổn, nhưng có thể cải thiện chút nữa.", 3.7, new Date()));
        allComments.add(new CommentModel(3, R.drawable.baseline_account_circle_24, "Tran Thi C",
                "Không ngon như tôi mong đợi, tôi thất vọng.", 2.0, new Date()));
        allComments.add(new CommentModel(4, R.drawable.baseline_account_circle_24, "Pham Minh D",
                "Món ăn tuyệt vời, sẽ quay lại lần sau!", 5.0, new Date()));
        allComments.add(new CommentModel(5, R.drawable.baseline_account_circle_24, "Nguyen Thi E",
                "Giá cả hơi cao nhưng chất lượng tốt.", 4.0, new Date()));
        allComments.add(new CommentModel(6, R.drawable.baseline_account_circle_24, "Nguyen Van A",
                "Món ăn ngon, tôi rất thích!", 4.5, new Date()));
        allComments.add(new CommentModel(7, R.drawable.baseline_account_circle_24, "Le Thi B",
                "Món ăn khá ổn, nhưng có thể cải thiện chút nữa.", 3.7, new Date()));
        allComments.add(new CommentModel(8, R.drawable.baseline_account_circle_24, "Tran Thi C",
                "Không ngon như tôi mong đợi, tôi thất vọng.", 2.0, new Date()));
        allComments.add(new CommentModel(9, R.drawable.baseline_account_circle_24, "Pham Minh D",
                "Món ăn tuyệt vời, sẽ quay lại lần sau!", 5.0, new Date()));
        allComments.add(new CommentModel(10, R.drawable.baseline_account_circle_24, "Nguyen Thi E",
                "Giá cả hơi cao nhưng chất lượng tốt.", 4.0, new Date()));
        allComments.add(new CommentModel(11, R.drawable.baseline_account_circle_24, "Nguyen Van A",
                "Món ăn ngon, tôi rất thích!", 4.5, new Date()));
        allComments.add(new CommentModel(12, R.drawable.baseline_account_circle_24, "Le Thi B",
                "Món ăn khá ổn, nhưng có thể cải thiện chút nữa.", 3.7, new Date()));
        allComments.add(new CommentModel(13, R.drawable.baseline_account_circle_24, "Tran Thi C",
                "Không ngon như tôi mong đợi, tôi thất vọng.", 2.0, new Date()));
        allComments.add(new CommentModel(14, R.drawable.baseline_account_circle_24, "Pham Minh D",
                "Món ăn tuyệt vời, sẽ quay lại lần sau!", 5.0, new Date()));
        allComments.add(new CommentModel(15, R.drawable.baseline_account_circle_24, "Nguyen Thi E",
                "Giá cả hơi cao nhưng chất lượng tốt.", 4.0, new Date()));
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

    private List<ImageModel> getImagesList() {
        List<ImageModel> list = new ArrayList<>();
        list.add(new ImageModel(R.drawable.quangcao));
        list.add(new ImageModel(R.drawable.coffee));
        list.add(new ImageModel(R.drawable.companypizza));
        list.add(new ImageModel(R.drawable.food_sample));
        return list;
    }
}
