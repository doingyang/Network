package com.project.network;

import android.app.Application;

import com.library.network.HttpHelper;
import com.library.network.processor.OkHttpProcessor;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化OkHttp方式网络请求代理
        HttpHelper.init(new OkHttpProcessor(this));
    }

}