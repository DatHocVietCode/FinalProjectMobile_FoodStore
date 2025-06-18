package com.example.app_foodstore.Manager;

import android.content.SharedPreferences;

import com.example.app_foodstore.APIService.User.APIServiceAuth;
import com.example.app_foodstore.Model.response.UserLoginRes;
import com.example.app_foodstore.Utils.UserUtils;

import java.io.IOException;

import retrofit2.Response;

public class TokenManager {
    private final SharedPreferences prefs;

    public TokenManager(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public String getAccessToken() {
        return prefs.getString("access_token", null);
    }

    public void saveAccessToken(String token) {
        prefs.edit().putString("access_token", token).apply();
    }

    public String getRefreshToken() {
        return prefs.getString("refresh_token", "");
    }

    public void saveRefreshToken(String token) {
        prefs.edit().putString("refresh_token", token).apply();
    }
}
