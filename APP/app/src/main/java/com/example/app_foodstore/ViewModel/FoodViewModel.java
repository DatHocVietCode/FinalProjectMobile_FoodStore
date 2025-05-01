package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.Repo.FoodRepository;

import java.util.List;

public class FoodViewModel extends ViewModel {
    private FoodRepository foodRepository;

    public FoodViewModel() {
        foodRepository = new FoodRepository();
    }
    public LiveData<List<FoodModel>> getBestSellerFoodList(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        return foodRepository.getBestSeller(keyword, categoryId, sortByName, sortByPrice);
    }
    public LiveData<List<FoodModel>> getNewFoodList(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        return foodRepository.getNewFood(keyword, categoryId, sortByName, sortByPrice);
    }
    public LiveData<List<FoodModel>> getFoods(String keyword, Long categoryId, String sortByName, String sortByPrice) {
        return foodRepository.getFoods(keyword, categoryId, sortByName, sortByPrice);
    }
}
