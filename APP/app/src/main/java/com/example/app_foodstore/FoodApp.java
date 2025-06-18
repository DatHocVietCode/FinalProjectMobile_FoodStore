package com.example.app_foodstore;

import android.app.Application;
import android.content.Context;

public class FoodApp extends Application {
    private static FoodApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static FoodApp getInstance() {
        return instance;
    }
    public static Context getAppContext()
    {
        return instance.getApplicationContext();
    }
}
