package com.example.app_foodstore.Model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginRes implements Serializable {

    private UserLogin user;

    @SerializedName("access_token")
    private String accessToken;

    public UserLogin getUserLogin() {
        return user;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.user = userLogin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
