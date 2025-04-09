package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_foodstore.Adapter.ImageViewPager2Adapter;
import com.example.app_foodstore.Model.ImageModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Transformer.DepthPageTransfomer;
import com.example.app_foodstore.Transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class FoodDetailActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<ImageModel> imagesList;
    private Handler handler = new Handler();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_detail);
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
