package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.FoodTabLayoutAdapter;
import com.example.app_foodstore.Adapter.OrderHistoryAdapter;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.ViewModel.FoodViewModel;
import com.example.app_foodstore.databinding.FragmentOrderHistoryBinding;
import com.example.app_foodstore.databinding.FragmentRcFoodDisplayBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_foodDisplay extends Fragment {
    FragmentRcFoodDisplayBinding binding;
    FoodViewModel foodViewModel;
    String keyword;
    Long categoryId;
    String sortByName;
    String sortByPrice;
    int tabNum;
    FoodTabLayoutAdapter adapter;

    // Constructor không cần tham số, sử dụng setArguments() thay cho constructor.
    public Fragment_foodDisplay() {
        // Default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get arguments from Bundle
        if (getArguments() != null) {
            keyword = getArguments().getString("keyword", "");  // Default value là ""
            categoryId = getArguments().getLong("categoryId", -1L);  // Default là -1L nếu không có giá trị
            if (categoryId == -1L) {
                categoryId = null;  // Nếu không có categoryId, truyền null để API xử lý
            }
            sortByName = getArguments().getString("sortByName", "");  // Default value là ""
            sortByPrice = getArguments().getString("sortByPrice", "");  // Default value là ""
        }

        binding = FragmentRcFoodDisplayBinding.inflate(inflater, container, false);
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentFoodDisplayRc.setLayoutManager(layoutManager);

        // Gọi phương thức getFoods() trong ViewModel và quan sát LiveData
        foodViewModel.getFoods(keyword, categoryId, sortByName, sortByPrice).observe(getViewLifecycleOwner(), foods -> {
            if (foods != null && !foods.isEmpty()) {
                adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                binding.fragmentFoodDisplayRc.setAdapter(adapter);
            } else {
                // Xử lý nếu không có dữ liệu (Hiển thị thông báo hay giao diện khác)
                Log.d("Fragment_foodDisplay", "onCreateView: No food items found");
            }
        });

        return binding.getRoot();
    }
}

