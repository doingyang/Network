package com.library.retrofit.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallback<T> {

    protected Type genericType;

    public HttpCallback() {
        initGenericType();
    }

    private void initGenericType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.genericType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.genericType = Object.class;
        }
    }

    public Type getGenericType() {
        return genericType;
    }

    public abstract void onResolve(T t);

    public abstract void onFailed(String errCode, String message);
}