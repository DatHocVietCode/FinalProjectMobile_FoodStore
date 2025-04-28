package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.FoodTabLayoutAdapter;
import com.example.app_foodstore.Adapter.OrderHistoryAdapter;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.databinding.FragmentOrderHistoryBinding;
import com.example.app_foodstore.databinding.FragmentRcFoodDisplayBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_foodDisplay extends Fragment {
    FragmentRcFoodDisplayBinding binding;
    int tabNum;
    public Fragment_foodDisplay(int tabNum) {
        this.tabNum = tabNum;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRcFoodDisplayBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentFoodDisplayRc.setLayoutManager(layoutManager);
        List<FoodModel> listFood= new ArrayList<>();
        listFood.add(new FoodModel("Category1", "Food1", 1));
        listFood.add(new FoodModel("Category1", "Food1", 1));
        listFood.add(new FoodModel("Category1", "Food1", 1));
        listFood.add(new FoodModel("Category1", "Food1", 1));
        listFood.add(new FoodModel("Category1", "Food1", 1));
        FoodTabLayoutAdapter adapter = new FoodTabLayoutAdapter(getContext(), listFood, tabNum);
        binding.fragmentFoodDisplayRc.setAdapter(adapter);
        return binding.getRoot();
    }
}
