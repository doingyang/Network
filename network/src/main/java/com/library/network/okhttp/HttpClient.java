package com.library.network.okhttp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * OkHttp请求实际执行类
 */
public class HttpClient {

    /*请求类型：GET*/
    public static final String HTTP_METHOD_GET = "METHOD_GET";
    /*请求类型：POST*/
    public static final String HTTP_METHOD_POST = "METHOD_POST";

    /*连接超时*/
    private static final long TIME_OUT_CONNECT = 30;
    /*读取超时*/
    private static final long TIME_OUT_READ = 30;
    /*写入超时*/
    private static final long TIME_OUT_WRITE = 60;

    private static OkHttpClient mOkHttpClient;

    private HttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //连接超时
        builder.connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS);
        //读取超时
        builder.readTimeout(TIME_OUT_READ, TimeUnit.SECONDS);
        //写入超时
        builder.writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS);
        //日志拦截器
        builder.addInterceptor(new HttpLoggingInterceptor());

        mOkHttpClient = builder.build();
    }

    private static class InstanceHolder {
        public static HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void reqHttp(String httpMethod, String url, Map<String, Object> params, Callback callBack) {
        if (httpMethod.equals(HTTP_METHOD_GET)) {
            get(url, params, callBack);
        } else if (httpMethod.equals(HTTP_METHOD_POST)) {
            post(url, params, callBack);
        }
    }

    private void get(String url, Map<String, Object> params, Callback callBack) {
        StringBuilder sb = new StringBuilder(url);
        if (null != params && params.size() > 0) {
            if (!url.endsWith("?")) {
                sb.append("?");
            }
            List<String> keys = new ArrayList<>(params.keySet());
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = params.get(key).toString();
                sb.append(key);
                sb.append("=");
                sb.append(value);
                if (i != keys.size() - 1) {
                    sb.append("&");
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(callBack);
    }

    private void post(String url, Map<String, Object> params, Callback callback) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (null != params && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue().toString());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
}