package com.library.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件上传拦截器
 */
public class UploadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        return chain.proceed(request);
    }

    public static UploadInterceptor create() {
        return new UploadInterceptor();
    }
}