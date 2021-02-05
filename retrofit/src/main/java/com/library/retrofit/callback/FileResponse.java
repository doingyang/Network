package com.library.retrofit.callback;

public interface FileResponse {

    void onSuccess();

    void onFailure(Throwable throwable, String content);
}