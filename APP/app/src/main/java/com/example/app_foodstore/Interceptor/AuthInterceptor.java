package com.example.app_foodstore.Interceptor;

import androidx.annotation.NonNull;

import com.example.app_foodstore.APIService.APIResponse;
import com.example.app_foodstore.APIService.Client.TokenRefreshClient;
import com.example.app_foodstore.APIService.Token.TokenRefreshService;
import com.example.app_foodstore.Manager.TokenManager;
import com.example.app_foodstore.Model.response.AccessTokenResponse;
import com.example.app_foodstore.Repo.AuthRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = tokenManager.getAccessToken(); // token đã lưu

        if (token != null && !token.isEmpty())
        {
            Request requestWithToken = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();
            Response response = chain.proceed(requestWithToken);

            // Nếu token hết hạn
            if (response.code() == 401) {
                response.close(); // đóng response cũ
                synchronized (this) {
                    String refreshToken = tokenManager.getRefreshToken(); // refresh token đã lưu
                    String newAccessToken = refreshTokenSync(refreshToken);
                    tokenManager.saveAccessToken(newAccessToken);
                    Request newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer " + newAccessToken)
                            .build();

                    return chain.proceed(newRequest);
                }
            }
            return response;
        }
        return chain.proceed(originalRequest);
    }
    private String refreshTokenSync(String refreshToken) throws IOException {
        TokenRefreshService service = TokenRefreshClient.getInstance().create(TokenRefreshService.class);
        APIResponse<AccessTokenResponse> response = service.refreshToken(refreshToken).execute().body();
        if (response != null) {
            return response.getData().get(0).getAccessToken();
        } else {
            throw new IOException("Failed to refresh token");
        }
    }

}
