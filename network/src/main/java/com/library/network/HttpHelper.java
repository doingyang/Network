package com.library.network;

import com.library.network.processor.IHttpProcessor;

import java.util.Map;

import okhttp3.Callback;

/**
 * 请求代理类
 */
public class HttpHelper {

    private HttpHelper() {
    }

    private static class InstanceHolder {
        public static HttpHelper INSTANCE = new HttpHelper();
    }

    public static HttpHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static IHttpProcessor mHttpProcessor;

    /**
     * 初始化设置请求代理
     */
    public static void init(IHttpProcessor httpProcessor) {
        mHttpProcessor = httpProcessor;
    }

    /**
     * GET请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 请求回调
     */
    public void get(String url, Map<String, Object> params, Callback callBack) {
        mHttpProcessor.get(url, params, callBack);
    }

    /**
     * POST请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 请求回调
     */
    public void post(String url, Map<String, Object> params, Callback callBack) {
        mHttpProcessor.post(url, params, callBack);
    }
}