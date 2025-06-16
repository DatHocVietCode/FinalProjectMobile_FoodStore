package com.example.app_foodstore.Interceptor;

import com.example.app_foodstore.Manager.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = tokenManager.getAccessToken(); // token đã lưu

        Request requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();

        Response response = chain.proceed(requestWithToken);

        // Nếu token hết hạn
        if (response.code() == 401) {
            response.close(); // đóng response cũ
            synchronized (this) {
                String newAccessToken = tokenManager.refreshToken(); // gọi /auth/refresh
                tokenManager.saveAccessToken(newAccessToken);

                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + newAccessToken)
                        .build();

                return chain.proceed(newRequest);
            }
        }

        return response;
    }
}
