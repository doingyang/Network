package com.library.network.okhttp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 日志拦截器
 */
public class HttpLoggingInterceptor implements Interceptor {

    private static final String TAG = "OkHttp";

    /**
     * chain里面包含了request和response
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder().build();

        // 开始时间
        long startTime = System.currentTimeMillis();
        Log.i(TAG, "RequestUrl=" + request.url());
        String method = request.method();
        if ("POST".equals(method)) {
            try {
                JSONObject jsonObject = new JSONObject();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        jsonObject.put(body.encodedName(i), body.encodedValue(i));
                    }
                    Log.i(TAG, "入参JSON=" + jsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Response response = chain.proceed(request);
        /*
         * 这里不能直接使用response.body().string()的方式输出日志
         * 因为response.body().string()之后，response中的流会被关闭，程序会报错，需要创建出一个新的response给应用层处理
         */
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.i(TAG, "出参JSON=" + responseBody.string());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        Log.i(TAG, "----------耗时:" + duration + "毫秒----------");
        return response;
    }
}