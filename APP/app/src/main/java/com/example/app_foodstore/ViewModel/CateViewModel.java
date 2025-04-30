package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.Repo.CategoryRepo;

import java.util.List;

public class CateViewModel extends ViewModel {
    private CategoryRepo cateRepository;
    private LiveData<List<CategoryModel>> allCate;
    public CateViewModel() {
        cateRepository = new CategoryRepo();
        allCate = cateRepository.getCategories(); // Lấy dữ liệu từ Repository
    }
    public LiveData<List<CategoryModel>> getAllCate() {
        return allCate;
    }
}
