package com.example.app_foodstore.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_foodstore.APIService.Client.RetrofitClient;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceAuth;
import com.example.app_foodstore.Manager.TokenManager;
import com.example.app_foodstore.Model.request.OTPRequestDTO;
import com.example.app_foodstore.Model.request.UserLoginReq;
import com.example.app_foodstore.Model.request.UserSignUpRequest;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Model.response.UserSignUpResponse;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final APIServiceAuth authService;

    public AuthRepository() {
        authService = RetrofitClient.getRetrofitInstance().create(APIServiceAuth.class);
    }

    public LiveData<UserLoginRes> login(UserLoginReq req) {
        MutableLiveData<UserLoginRes> loginData = new MutableLiveData<>();

        Call<BaseResponse<UserLoginRes>> call = authService.login(req);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<UserLoginRes>> call, Response<BaseResponse<UserLoginRes>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        loginData.setValue(response.body().getData());
                        Log.d("AuthRepo", "Login success: " + response.body().getData().getAccessToken());
                    } else {
                        Log.d("AuthRepo", response.message());
                        loginData.setValue(null);
                    }
                } else {
                    Log.d("AuthRepo", "Response unsuccessful or body null");
                    loginData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserLoginRes>> call, Throwable t) {
                Log.e("AuthRepo", "Login failed: " + t.getMessage());
                loginData.setValue(null);
            }
        });

        return loginData;
    }

    public LiveData<UserSignUpResponse> signUp(UserSignUpRequest req) {
        MutableLiveData<UserSignUpResponse> signUpData = new MutableLiveData<>();
        Call<BaseResponse<UserSignUpResponse>> call = authService.signUp(req);
        call.enqueue(new Callback<BaseResponse<UserSignUpResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserSignUpResponse>> call, Response<BaseResponse<UserSignUpResponse>> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    if (response.body().getStatus() == 200)
                    {
                        signUpData.setValue(response.body().getData());
                        Log.d("AuthRepo", "Sign up success!");
                    }
                }
                else {
                    try {
                        String errorJson = response.errorBody().string();  // Lấy nội dung body khi lỗi
                        JSONObject jsonObject = new JSONObject(errorJson);
                        String message = jsonObject.getString("message");

                        Log.d("AuthRepo", "Error message from server: " + message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("AuthRepo", "Error parsing error body");
                    }

                    signUpData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<UserSignUpResponse>> call, Throwable t) {
                Log.d("AuthRepo", "Sign up failed due to" + t.getMessage());
                signUpData.setValue(null);
            }
        });
        return signUpData;
    }

    public LiveData<String> resendOTP() {
        MutableLiveData<String> resendOTPData = new MutableLiveData<>();
        Call<BaseResponse<String>> call = authService.resendOTP();
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    if (response.body().getStatus() == 200)
                    {
                        resendOTPData.setValue(response.body().getData());
                        Log.d("AuthRepo", "Resend OTP success!");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

            }
        });
        return resendOTPData;
    }

    public LiveData<String> verifyOTP(OTPRequestDTO otpRequestDTO)
    {
        MutableLiveData<String> verifyOTPData = new MutableLiveData<>();
        Call<BaseResponse<String>> call = authService.verifyOTP(otpRequestDTO);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    BaseResponse<String> baseResponse = response.body();
                    verifyOTPData.setValue(baseResponse.getMessage());
                    Log.d("AuthRepo", "Verify OTP success: " + baseResponse.getMessage());
                }
                else
                {
                    if (response.errorBody() != null) {
                        String error = response.errorBody().toString();
                        Log.d("AuthRepo", "Error body: " + error);
                        // Giải mã JSON nếu cần, hoặc dùng một thư viện như Gson để convert về BaseResponse
                        verifyOTPData.setValue("OTP verification failed!");
                    } else {
                        verifyOTPData.setValue("Server returned no response!");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                Log.d("AuthRepo", "Verify OTP failed: " + t.getMessage());
                verifyOTPData.setValue("Cannot connect to server.");
            }
        });

        return verifyOTPData;
    }
    public String refreshTokenSync(String refreshToken) throws IOException {
        Response<UserLoginRes> response = authService.refreshToken(refreshToken).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body().getAccessToken();
        } else {
            throw new IOException("Token refresh failed");
        }
    }
}
