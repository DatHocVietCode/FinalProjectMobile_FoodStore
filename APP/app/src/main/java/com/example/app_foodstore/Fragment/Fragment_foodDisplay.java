package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.FoodTabLayoutAdapter;
import com.example.app_foodstore.Adapter.OrderHistoryAdapter;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.ViewModel.FilterViewModel;
import com.example.app_foodstore.ViewModel.FoodViewModel;
import com.example.app_foodstore.databinding.FragmentOrderHistoryBinding;
import com.example.app_foodstore.databinding.FragmentRcFoodDisplayBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_foodDisplay extends Fragment {
    FragmentRcFoodDisplayBinding binding;
    FoodViewModel foodViewModel;
    String keyword = "";
    Long categoryId;
    String sortByName;
    String sortByPrice;
    int tabNum;
    FoodTabLayoutAdapter adapter;
    FilterViewModel filterViewModel;
    // Constructor không cần tham số, sử dụng setArguments() thay cho constructor.
    public Fragment_foodDisplay() {
        // Default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getArgumentsFromParent();
        initViewModel();
        initView(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
    }

    private void initObservers() {
        if (filterViewModel == null) {
            Log.e("observe", "initObservers: filterViewModel is null");
            return;
        }

        // Các biến để lưu giá trị cũ
        final Long[] lastCategoryId = {null};
        final String[] lastSortByName = {null};
        final String[] lastSortByPrice = {null};

        // Quan sát sự thay đổi của CategoryId
        filterViewModel.getCategoryId().observe(getViewLifecycleOwner(), catId -> {
            if ((lastCategoryId[0] == null && catId != null && catId != 0L) ||
                    (lastCategoryId[0] != null && !lastCategoryId[0].equals(catId))) {
                lastCategoryId[0] = catId;
                reloadData();
                Log.d("observe", "CategoryId changed to " + catId);
            }
        });

        // Quan sát sự thay đổi của SortByName
        filterViewModel.getSortByName().observe(getViewLifecycleOwner(), nameSort -> {
            if ((lastSortByName[0] == null && nameSort != null && !nameSort.isEmpty()) ||
                    (lastSortByName[0] != null && !lastSortByName[0].equals(nameSort))) {
                lastSortByName[0] = nameSort;
                reloadData();
                Log.d("observe", "SortByName changed to " + nameSort);
            }
        });

        // Quan sát sự thay đổi của SortByPrice
        filterViewModel.getSortByPrice().observe(getViewLifecycleOwner(), priceSort -> {
            if ((lastSortByPrice[0] == null && priceSort != null && !priceSort.isEmpty()) ||
                    (lastSortByPrice[0] != null && !lastSortByPrice[0].equals(priceSort))) {
                lastSortByPrice[0] = priceSort;
                reloadData();
                Log.d("observe", "SortByPrice changed to " + priceSort);
            }
        });
    }


    private void initView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentRcFoodDisplayBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentFoodDisplayRc.setLayoutManager(layoutManager);
        switch (tabNum)
        {
            case 0:
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
                break;
            case 1:
                foodViewModel.getBestSellerFoodList("", null, "", "").observe(getViewLifecycleOwner(), newFoodList -> {
                    if (newFoodList != null && !newFoodList.isEmpty()) {
                        adapter = new FoodTabLayoutAdapter(getContext(), newFoodList, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                    } else {
                        // Xử lý nếu không có dữ liệu (Hiển thị thông báo hay giao diện khác)
                        Log.d("Fragment_foodDisplay", "onCreateView: No food items found");
                    }
                });
                break;
            case 2:
                foodViewModel.getNewFoodList("", null, "", "").observe(getViewLifecycleOwner(), newFoodList -> {
                    if (newFoodList != null && !newFoodList.isEmpty()) {
                        adapter = new FoodTabLayoutAdapter(getContext(), newFoodList, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                    } else {
                        // Xử lý nếu không có dữ liệu (Hiển thị thông báo hay giao diện khác)
                        Log.d("Fragment_foodDisplay", "onCreateView: No food items found");
                    }
                });
                break;
            default:
        }
    }

    private void initViewModel() {
        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);
        filterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
    }

    private void getArgumentsFromParent() {
        if (getArguments() != null) {
            keyword = getArguments().getString("keyword", "");  // Default value là ""
            categoryId = getArguments().getLong("categoryId", -1L);  // Default là -1L nếu không có giá trị
            if (categoryId == -1L) {
                categoryId = null;  // Nếu không có categoryId, truyền null để API xử lý
            }
            sortByName = getArguments().getString("sortByName", "");  // Default value là ""
            sortByPrice = getArguments().getString("sortByPrice", "");  // Default value là ""
            tabNum = getArguments().getInt("tabNum", 0);  // Default value là 0
        }
    }

    private void reloadData() {
        Long catId = filterViewModel.getCategoryId().getValue();
        if (catId != null && catId == 0L) {
            catId = null;
        }

        String nameSort = filterViewModel.getSortByName().getValue();
        String priceSort = filterViewModel.getSortByPrice().getValue();

        switch (tabNum) {
            case 0:
                foodViewModel.getFoods(keyword, catId, nameSort, priceSort).removeObservers(getViewLifecycleOwner());
                foodViewModel.getFoods(keyword, catId, nameSort, priceSort).observe(getViewLifecycleOwner(), foods -> {
                    if (foods != null && !foods.isEmpty()) {
                        adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;

            case 1:
                foodViewModel.getBestSellerFoodList(keyword, catId, nameSort, priceSort).removeObservers(getViewLifecycleOwner());
                foodViewModel.getBestSellerFoodList(keyword, catId, nameSort, priceSort).observe(getViewLifecycleOwner(), foods -> {
                    if (foods != null && !foods.isEmpty()) {
                        adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;

            case 2:
                foodViewModel.getNewFoodList(keyword, catId, nameSort, priceSort).removeObservers(getViewLifecycleOwner());
                foodViewModel.getNewFoodList(keyword, catId, nameSort, priceSort).observe(getViewLifecycleOwner(), foods -> {
                    if (foods != null && !foods.isEmpty()) {
                        adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }


}

