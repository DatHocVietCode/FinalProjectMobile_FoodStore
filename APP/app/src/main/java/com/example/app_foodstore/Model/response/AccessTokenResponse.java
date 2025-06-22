package com.example.app_foodstore.Model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessTokenResponse implements Serializable {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
