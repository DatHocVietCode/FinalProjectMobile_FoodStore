package com.example.app_foodstore.Adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_foodstore.Fragment.Fragment_foodDisplay;

public class ViewPagerFoodTabLayoutAdapter extends FragmentStateAdapter {
    private String keyword;
    private Long categoryId;
    private String sortByName;
    private String sortByPrice;

    public ViewPagerFoodTabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle
            , String keyword, Long categoryId, String sortByName, String sortByPrice) {
        super(fragmentManager, lifecycle);
        this.keyword = (keyword != null) ? keyword : "";  // Mặc định là chuỗi rỗng
        this.categoryId = (categoryId != null) ? categoryId : 0L;  // Mặc định là 0L nếu categoryId là null
        this.sortByName = (sortByName != null) ? sortByName : "";  // Mặc định là chuỗi rỗng
        this.sortByPrice = (sortByPrice != null) ? sortByPrice : "";  // Mặc định là chuỗi rỗng
        Log.d("ViewPagerFoodTabLayoutAdapter", "ViewPagerFoodTabLayoutAdapter: " + keyword + " " + categoryId + " " + sortByName + " " + sortByPrice);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment_foodDisplay fragment = new Fragment_foodDisplay();
        // Tạo Bundle để truyền tham số vào Fragment
        Bundle args = new Bundle();
        args.putLong("categoryId", categoryId);
        args.putInt("tabNum", position);
        fragment.setArguments(args);  // Đặt Bundle vào Fragment
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng Fragment (tabs)
    }

    // Getter và Setter cho các tham số
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortByPrice() {
        return sortByPrice;
    }

    public void setSortByPrice(String sortByPrice) {
        this.sortByPrice = sortByPrice;
    }
}
