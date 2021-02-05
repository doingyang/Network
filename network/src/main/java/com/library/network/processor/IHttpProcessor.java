package com.library.network.processor;

import java.util.Map;

import okhttp3.Callback;

public interface IHttpProcessor {

    /**
     * GET请求
     */
    void get(String url, Map<String, Object> params, Callback callback);

    /**
     * POST请求
     */
    void post(String url, Map<String, Object> params, Callback callback);
}