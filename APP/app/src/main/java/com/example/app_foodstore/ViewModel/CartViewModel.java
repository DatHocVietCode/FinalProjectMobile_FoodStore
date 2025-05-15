package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.CartModel;
import com.example.app_foodstore.Repo.CartRepository;

import java.util.List;

public class CartViewModel extends ViewModel {
    private final CartRepository repository;
    private final MutableLiveData<Boolean> updateStatus = new MutableLiveData<>();

    public CartViewModel() {
        repository = new CartRepository();
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public LiveData<List<CartModel>> getMyCart(String token) {
        return repository.getMyCart(token);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng, trả về trạng thái thành công/thất bại
    public LiveData<Boolean> updateCartItem(String token, Long productId, Integer quantity) {
        return repository.updateCartItem(token, productId, quantity);
    }
    public LiveData<Boolean> deleteCartItem(String token, Long productId) {
        return repository.deleteCartItem(token, productId);
    }

    public LiveData<Boolean> addCartItem(String token, Long productId, Integer quantity) {
        return repository.addCartItem(token, productId, quantity);
    }
}
