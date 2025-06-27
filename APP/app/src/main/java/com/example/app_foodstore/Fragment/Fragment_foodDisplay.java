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
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.ViewModel.FilterViewModel;
import com.example.app_foodstore.ViewModel.FoodViewModel;
import com.example.app_foodstore.databinding.FragmentRcFoodDisplayBinding;

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
        initView(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initObservers();
    }

    private void initObservers() {
        if (filterViewModel == null) {
            Log.e("observe", "initObservers: filterViewModel is null");
            return;
        }

        filterViewModel.setFilters(categoryId, sortByName, sortByPrice);
        MediatorLiveData<Object> mediator = new MediatorLiveData<>();
        mediator.addSource(filterViewModel.getCategoryId(), val -> mediator.setValue(new Object()));
        mediator.addSource(filterViewModel.getSortByName(), val -> mediator.setValue(new Object()));
        mediator.addSource(filterViewModel.getSortByPrice(), val -> mediator.setValue(new Object()));
        mediator.observe(getViewLifecycleOwner(), val -> reloadData());
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentRcFoodDisplayBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentFoodDisplayRc.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);
        filterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
        filterViewModel.setFilters(categoryId, sortByName, sortByPrice);
    }

    private void getArgumentsFromParent() {
        if (getArguments() != null) {
            keyword = getArguments().getString("keyword", "");  // Default value là ""
            categoryId = getArguments().getLong("categoryId", 0L);
            if (categoryId == 0L) {
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
        //Log.d("reloadData", "reloadData: categoryId" + catId + " nameSort:" + nameSort + " priceSort:" + priceSort + " tabnum" + tabNum);
        switch (tabNum) {
            case 0:
                foodViewModel.getFoods(keyword, catId, nameSort, priceSort).removeObservers(getViewLifecycleOwner());
                foodViewModel.getFoods(keyword, catId, nameSort, priceSort).observe(getViewLifecycleOwner(), foods -> {
                    if (foods != null) {
                        adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                        binding.fragmentFoodDisplayTvNoProduct.setVisibility(View.INVISIBLE);
                        adapter.notifyDataSetChanged();
                        if (foods.isEmpty())
                        {
                            binding.fragmentFoodDisplayTvNoProduct.setVisibility(View.VISIBLE);
                        }
                    }
                });
                break;
            case 1:
                foodViewModel.getBestSellerFoodList(keyword, catId, nameSort, priceSort).removeObservers(getViewLifecycleOwner());
                foodViewModel.getBestSellerFoodList(keyword, catId, nameSort, priceSort).observe(getViewLifecycleOwner(), foods -> {
                    if (foods != null) {
                        for (FoodModel food:
                                foods) {
                            Log.d("reloadData", "foods1 is not null: foodName " + food.getName());
                        }
                        adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                        binding.fragmentFoodDisplayTvNoProduct.setVisibility(View.INVISIBLE);
                        Log.d("reloadData", "reloadData: foods1 is not null " + foods.get(0).getCategory_id() + " nameSort:" + nameSort + " priceSort:" + priceSort );
                        adapter.notifyDataSetChanged();
                        if (foods.isEmpty())
                        {
                            binding.fragmentFoodDisplayTvNoProduct.setVisibility(View.VISIBLE);
                            Log.d("reloadData", "reloadData: foods1 is empty" );
                        }
                    }
                    else
                    {
                        Log.d("reloadData", "reloadData: foods1 is null" );
                    }
                });
                break;

            case 2:
                foodViewModel.getNewFoodList(keyword, catId, nameSort, priceSort).removeObservers(getViewLifecycleOwner());
                foodViewModel.getNewFoodList(keyword, catId, nameSort, priceSort).observe(getViewLifecycleOwner(), foods -> {
                    if (foods != null) {
                        for (FoodModel food:
                             foods) {
                            Log.d("reloadData", "foods2 is not null: foodName " + food.getName());
                        }
                        adapter = new FoodTabLayoutAdapter(getContext(), foods, tabNum);
                        binding.fragmentFoodDisplayRc.setAdapter(adapter);
                        binding.fragmentFoodDisplayTvNoProduct.setVisibility(View.INVISIBLE);
                        Log.d("reloadData", "reloadData: foods2 is not null " + foods.get(0).getCategory_id() + " nameSort:" + nameSort + " priceSort:" + priceSort );
                        adapter.notifyDataSetChanged();
                        if (foods.isEmpty())
                        {
                            binding.fragmentFoodDisplayTvNoProduct.setVisibility(View.VISIBLE);
                            Log.d("reloadData", "reloadData: foods1 is empty" );
                        }
                    }
                    else
                    {
                        Log.d("reloadData", "reloadData: foods2 is null" );
                    }
                });
                break;
        }
    }
}

