package com.example.app_foodstore.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.app_foodstore.Activity.LoginActivity;

public class UserUtils {
    public static boolean checkUser(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user_prefs", Activity.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (!isLoggedIn) {
            return false;
        }
        return true;
    }
    public static String getTokenFromPreferences(Context context) {
         SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
         return sharedPreferences.getString("access_token", "");
    }

    public static void notifyLogin(Activity activity)
    {
        new AlertDialog.Builder(activity)
                    .setTitle("Yêu cầu đăng nhập")
                    .setMessage("Bạn có muốn đăng nhập không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    })
                    .setNegativeButton("Không", null)
                    .show();
    }
}