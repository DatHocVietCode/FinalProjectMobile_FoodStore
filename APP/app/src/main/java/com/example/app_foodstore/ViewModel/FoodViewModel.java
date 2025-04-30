package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Repo.FoodRepository;

import java.util.List;

public class FoodViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private LiveData<List<FoodModel>> newFoodList;

    public FoodViewModel() {
        foodRepository = new FoodRepository();
        newFoodList = foodRepository.getNewFood(); // Lấy dữ liệu từ Repository
    }

    public LiveData<List<FoodModel>> getNewFoodList() {
        return newFoodList;
    }
    public LiveData<List<FoodModel>> getFoods(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        return foodRepository.getFoods(keyword, categoryId, sortByName, sortByPrice);
    }
}
