package com.example.app_foodstore.Manager;

import android.content.SharedPreferences;

import com.example.app_foodstore.APIService.User.APIServiceAuth;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Utils.UserUtils;

import java.io.IOException;

import retrofit2.Response;

public class TokenManager {
    private final SharedPreferences prefs;

    public TokenManager(SharedPreferences prefs, APIServiceAuth apiService) {
        this.prefs = prefs;
        this.apiService = apiService;
    }

    private UserUtils userUtils;
    private final APIServiceAuth apiService;

    public String getAccessToken() {
        return prefs.getString("access_token", null);
    }

    public void saveAccessToken(String token) {
        prefs.edit().putString("access_token", token).apply();
    }

    public String refreshToken() throws IOException {
        String refreshToken = prefs.getString("refresh_token", "");
        Response<UserLoginRes> response = apiService.refreshToken(refreshToken).execute();
        if (response.isSuccessful()) {
            String newAccess = response.body().getAccessToken();
            saveAccessToken(newAccess);
            return newAccess;
        } else {
            throw new IOException("Refresh token failed");
        }
    }
}
