package com.library.retrofit.callback;

import android.widget.Toast;

public abstract class ServerCallback<T, V> extends HttpCallback<T> {
    @Override
    public void onResolve(T t) {
        if (t instanceof ServerCallbackModel) {
            ServerCallbackModel<V> callbackData = (ServerCallbackModel) t;
            V result = callbackData.getData();
            if (callbackData.isSuccess()) {
                this.onSuccess(result);
            } else {
                onFailed(callbackData.getErr_code(), callbackData.getMessage());
            }
        } else {
            onSuccess((V) t);
        }
    }

    @Override
    public void onFailed(String error_code, String error_message) {
        onFailure(error_code, error_message);
    }

    public abstract void onSuccess(V data);

    public abstract void onFailure(String error_code, String error_message);
}