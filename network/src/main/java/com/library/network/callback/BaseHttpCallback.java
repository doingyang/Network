package com.library.network.callback;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.library.network.bean.ErrMsg;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.internal.annotations.EverythingIsNonNull;

/**
 * 请求结果数据回调统一处理
 * 由项目实际数据结构决定，子类继承此类重写相关方法
 */
public abstract class BaseHttpCallback<T> implements Callback {

    @EverythingIsNonNull
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful() && null != response.body()) {
            String json = response.body().string();
            parseResult(json);
            int code = getCode();
            String msg = getMsg();
            String data = getData();
            if (isSucceed()) {
                onSuccess(data);
            } else {
                onFailure(code, msg);
            }
        } else {
            onFailure(-1, "请求响应失败");
        }
    }

    @EverythingIsNonNull
    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(-1, e.getMessage());
    }

    private void onSuccess(String data) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onSuccess(dealData(data));
            }
        });
    }

    public void onFailure(int errCode, String errMsg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onFailure(new ErrMsg(errCode, errMsg));
            }
        });
    }

    /**
     * 请求成功回调
     */
    public abstract void onSuccess(T data);

    /**
     * 请求失败回调
     */
    public abstract void onFailure(ErrMsg errMsg);

    public void parseResult(String json) {

    }

    public int getCode() {
        return 0;
    }

    public String getMsg() {
        return "";
    }

    public String getData() {
        return "";
    }

    public boolean isSucceed() {
        return false;
    }

    /**
     * 处理结果数据
     */
    public T dealData(String data) {
        Gson gson = new Gson();
        // 注：这里传递this.getClass()，别用HttpCallback.class !!!
        Type typeOfT = getTypeOfT(this.getClass());
        if (typeOfT == String.class) {
            // 泛型是String，返回字符串
            return (T) data;
        } else {
            // 泛型是实体或者实体集合
            return gson.fromJson(data, typeOfT);
        }
    }

    /**
     * 获取泛型T的类型
     */
    public static Type getTypeOfT(Class<?> clazz) {
        Type typeOfT = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (null != types && types.length > 0) {
                typeOfT = types[0];
            }
        } else {
            typeOfT = Object.class;
        }
        return typeOfT;
    }
}