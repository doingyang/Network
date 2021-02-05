package com.library.retrofit.interceptor;

import android.content.Context;

import com.library.retrofit.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 如果离线缓存与在线缓存同时使用，在线的时候必须先将离线缓存清空
 */
public class CacheInterceptor implements Interceptor {

    private final String TAG = "CacheInterceptor";

    private Context mContext;

    public CacheInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response responseLatest;
        if (NetworkUtil.isNetworkConnected(mContext)) {
            // 有网失效一分钟
            int maxAge = 60;
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // 没网失效6小时
            int maxStale = 60 * 60 * 6;
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return responseLatest;
    }

    public static CacheInterceptor create(Context context) {
        return new CacheInterceptor(context);
    }
}