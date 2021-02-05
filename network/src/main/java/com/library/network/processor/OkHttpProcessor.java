package com.library.network.processor;

import android.content.Context;

import com.library.network.okhttp.HttpClient;

import java.util.Map;

import okhttp3.Callback;

public class OkHttpProcessor implements IHttpProcessor {

    private Context context;

    public OkHttpProcessor(Context context) {
        this.context = context;
    }

    @Override
    public void get(String url, Map<String, Object> params, Callback callback) {
        reqHttp(HttpClient.HTTP_METHOD_GET, url, params, callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, Callback callback) {
        reqHttp(HttpClient.HTTP_METHOD_POST, url, params, callback);
    }

    /**
     * 网络请求
     *
     * @param httpMethod 请求类型
     * @param url        请求地址
     * @param params     请求参数
     * @param callBack   结果回调
     */
    private void reqHttp(String httpMethod, String url, Map<String, Object> params, final Callback callBack) {
        /*if (!NetworkUtil.isNetWorkAvailable(context)) {
            return;
        }*/
        HttpClient.getInstance().reqHttp(httpMethod, url, params, callBack);
    }
}