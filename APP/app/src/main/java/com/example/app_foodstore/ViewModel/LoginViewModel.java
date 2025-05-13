package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Repo.AuthRepository;

public class LoginViewModel extends ViewModel {
    private final AuthRepository repository = new AuthRepository();

    public LiveData<UserLoginRes> login(UserLoginReq dto) {
        return repository.login(dto);
    }
}