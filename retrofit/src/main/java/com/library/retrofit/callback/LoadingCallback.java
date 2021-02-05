package com.library.retrofit.callback;

/**
 * 加载框回调
 */
public interface LoadingCallback {

    /**
     * 开始显示加载框
     */
    void onStartLoading(String msg);

    /**
     * 隐藏加载框
     */
    void onFinishLoading();
}