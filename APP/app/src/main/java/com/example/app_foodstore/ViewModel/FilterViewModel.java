package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FilterViewModel extends ViewModel {
    private final MutableLiveData<Long> categoryId = new MutableLiveData<>(0L);
    private final MutableLiveData<String> sortByName = new MutableLiveData<>("");
    private final MutableLiveData<String> sortByPrice = new MutableLiveData<>("");

    public LiveData<Long> getCategoryId() {
        return categoryId;
    }

    public LiveData<String> getSortByName() {
        return sortByName;
    }

    public LiveData<String> getSortByPrice() {
        return sortByPrice;
    }

    public void setFilters(Long catId, String nameSort, String priceSort) {
        categoryId.setValue(catId);
        sortByName.setValue(nameSort);
        sortByPrice.setValue(priceSort);
    }
}
