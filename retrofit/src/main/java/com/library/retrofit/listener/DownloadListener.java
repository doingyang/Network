package com.library.retrofit.listener;

public abstract class DownloadListener<T> {

    /**
     * 单个下载成功后回调
     */
    public abstract void onNext(T t);

    public void onStart() {
    }

    /**
     * 批量下载完成
     */
    public abstract void onComplete();

    public abstract void updateProgress(float contentRead, long contentLength, boolean completed);

    public abstract void onError(Throwable e);

    public void onPause() {

    }

    public void onStop() {

    }
}