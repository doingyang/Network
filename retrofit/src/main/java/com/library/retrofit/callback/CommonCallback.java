package com.library.retrofit.callback;

public abstract class CommonCallback<T> extends HttpCallback<T> {

    @Override
    public void onResolve(T t) {
        onSuccess(t);
    }

    @Override
    public void onFailed(String error_code, String error_message) {
        onFailure(error_code, error_message);
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(String error_code, String error_message);
}