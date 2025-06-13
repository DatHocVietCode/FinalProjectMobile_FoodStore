package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.request.UserSignUpRequest;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Model.response.UserSignUpResponse;
import com.example.app_foodstore.Repo.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository repository = new AuthRepository();

    public LiveData<UserLoginRes> login(UserLoginReq dto) {
        return repository.login(dto);
    }

    public LiveData<UserSignUpResponse> signUp(UserSignUpRequest dto) {
        return repository.signUp(dto);
    }

    public LiveData<String> resendOTP() {
        return  repository.resendOTP();
    }
}