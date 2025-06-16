package com.example.app_foodstore.APIService.Client;

import android.content.Context;

import com.example.app_foodstore.APIService.Cart.APIServiceCart;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceAuth;

public class APIClient {

    public static APIServiceAuth getAuthService() {
        return Constant.retrofit.create(APIServiceAuth.class);
    }

    public static APIServiceCart getCartService(Context context) {
        return RetrofitClient.getRetrofitInstance(context).create(APIServiceCart.class);
    }
}