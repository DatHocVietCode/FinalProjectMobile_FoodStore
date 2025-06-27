package com.example.app_foodstore.Utils;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUtils {
    public static MultipartBody.Part toMultipartBody(Context context, Uri uri, String formFieldName) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] bytes = getBytes(inputStream);
            RequestBody requestFile = RequestBody.create(
                    bytes,
                    MediaType.parse(context.getContentResolver().getType(uri))
            );
            String fileName = "avatar_" + System.currentTimeMillis() + ".jpg";
            return MultipartBody.Part.createFormData(formFieldName, fileName, requestFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
