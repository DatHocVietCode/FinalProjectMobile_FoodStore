package com.example.app_foodstore.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.app_foodstore.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class TrackOrderActivity extends AppCompatActivity {
    private ImageButton toggleButton;
    private BottomSheetBehavior<CardView> bottomSheetBehavior;
    List<View> viewList;
    List<ImageView> imageViewList;
    List<TextView> textViewList;
    TextView tv_breakdown;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        
        AnhXa();
    }

    private void AnhXa() {
        setupBottomCard();
        setupImg();
        setupStatus();
    }

    private void setupStatus() {
        getViewList();
        getImageViewList();
        getTextViewList();

        int currentState = 1; // Lấy từ API
        for (int i = 0; i <= currentState; i++) {
            imageViewList.get(i).setImageResource(R.drawable.icon_check);
            textViewList.get(i).setTextColor(getResources().getColor(R.color.trackOrder_check));
            if (i != 0)
            {
                int j = i - 1;
                viewList.get(j).setBackgroundResource(R.color.trackOrder_check);
            }
        }
    }

    private void getTextViewList() {
        textViewList = List.of(
                findViewById(R.id.trackOrder_tvStatus0),
                findViewById(R.id.trackOrder_tvStatus1),
                findViewById(R.id.trackOrder_tvStatus2),
                findViewById(R.id.trackOrder_tvStatus3)
        );
    }

    private void getImageViewList() {
        imageViewList = List.of(
                findViewById(R.id.trackOder_imgSt0),
                findViewById(R.id.trackOder_imgSt1),
                findViewById(R.id.trackOder_imgSt2),
                findViewById(R.id.trackOder_imgSt3)
        );
    }

    private void getViewList() {
        viewList = List.of(
                findViewById(R.id.trackOrder_view1),
                findViewById(R.id.trackOrder_view2),
                findViewById(R.id.trackOrder_view3)
        );
    }

    private void setupImg() {
        ImageView imageView = findViewById(R.id.trackOrder_imgTracking); // Đổi lại thành ID của bạn

        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 50f);
        animator.setDuration(1000); // Thời gian cho mỗi chu kỳ (1 giây)
        animator.setRepeatMode(ObjectAnimator.REVERSE); // Di chuyển ngược lại khi hết chu kỳ
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Lặp vô hạn
        animator.start();

    }

    private void setupBottomCard() {
        CardView bottomCard = findViewById(R.id.trackOrder_cardView_bottomSheet);
        toggleButton = findViewById(R.id.trackOrder_cardView_bottomSheet_expandbtn);
        tv_breakdown = findViewById(R.id.trackOrder_btn_breakDown);
        // Khởi tạo behavior từ CardView
        bottomSheetBehavior = BottomSheetBehavior.from(bottomCard);
        // Đặt chiều cao khi collapsed là 200dp
        int collapsedPeekDp = 200;
        float density = getResources().getDisplayMetrics().density;
        int collapsedPeekPx = (int) (collapsedPeekDp * density);
        bottomSheetBehavior.setPeekHeight(collapsedPeekPx);
        // Trạng thái ban đầu (expanded)
        bottomCard.post(() -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setHideable(false);
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

                if (currentState != BottomSheetBehavior.STATE_SETTLING) {
                    if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        // Không cần set lại peek height ở đây nữa vì đã đặt lúc khởi tạo
                        bottomSheetBehavior.setHideable(false);
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                } else {
                    Log.d("STate", "BottomSheet is settling, cannot change state");
                }
            }
        });

        tv_breakdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrderActivity.this, BreakDownOrderActivity.class);
                startActivity(intent);
            }
        });
    }

}
