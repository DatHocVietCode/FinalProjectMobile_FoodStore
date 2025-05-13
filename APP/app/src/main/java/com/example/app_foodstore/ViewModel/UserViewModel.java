package com.example.app_foodstore.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Model.response.UserRes;
import com.example.app_foodstore.Repo.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<UserRes> userProfile = new MutableLiveData<>();

    // Khởi tạo UserRepository
    public UserViewModel() {
        userRepository = new UserRepository();
    }

    // Lấy dữ liệu người dùng và cập nhật LiveData
    public void getUserProfile(String token) {
        userRepository.getUserProfile(token).observeForever(userRes -> {
            if (userRes != null) {
                userProfile.setValue(userRes); // Cập nhật dữ liệu người dùng
            }
        });
    }

    // Trả về LiveData (chỉ cho phép đọc, không cho phép sửa trực tiếp)
    public LiveData<UserRes> getUserProfileLiveData() {
        return userProfile;
    }

    // Factory cho ViewModelProvider để hỗ trợ tham số (nếu cần)
    public static class UserViewModelFactory implements ViewModelProvider.Factory {
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(UserViewModel.class)) {
                return (T) new UserViewModel();
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
