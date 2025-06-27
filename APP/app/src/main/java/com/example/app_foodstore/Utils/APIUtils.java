package com.example.app_foodstore.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class APIUtils {
    private RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }

}
