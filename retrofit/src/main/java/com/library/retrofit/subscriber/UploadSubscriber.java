package com.library.retrofit.subscriber;

import android.content.Context;
import android.text.TextUtils;

import com.library.retrofit.callback.FileResponse;
import com.library.retrofit.callback.LoadingCallback;

import okhttp3.ResponseBody;
import rx.Subscriber;

public class UploadSubscriber<T extends ResponseBody> extends Subscriber<T> {

    private Context mContext;
    private boolean showLoadingDialog = false;
    private String loadingMsg;

    private LoadingCallback loadingCallback;
    private FileResponse callback;

    /**
     * 不带加载框回调
     */
    public UploadSubscriber(Context context, FileResponse callback) {
        this.mContext = context;
        this.callback = callback;
    }

    /**
     * 带加载框回调
     */
    public UploadSubscriber(Context context, FileResponse callback, String loadingMsg) {
        this.mContext = context;
        this.callback = callback;
        this.loadingMsg = loadingMsg;

        if (!TextUtils.isEmpty(loadingMsg)) {
            try {
                loadingCallback = (LoadingCallback) context;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (loadingCallback != null) {
                showLoadingDialog = true;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showLoadingDialog) {
            loadingCallback.onStartLoading(loadingMsg);
        }
    }

    @Override
    public void onCompleted() {
        if (showLoadingDialog) {
            loadingCallback.onFinishLoading();
        }
        callback.onSuccess();
    }

    @Override
    public void onError(Throwable e) {
        if (showLoadingDialog) {
            loadingCallback.onFinishLoading();
        }
        callback.onFailure(e, "服务器错误");
    }

    @Override
    public void onNext(T t) {
        if (t.contentLength() == 0) {
            return;
        }
//        Gson gson = new Gson();
//        TypeAdapter<?> adapter = gson.getAdapter(callback.getGenericityType());
//        JsonReader jsonReader = gson.newJsonReader(t.charStream());
//        try {
//            callback.onNext(adapter.example(jsonReader));
//        } catch (IOException e) {
//            callback.onFailure(e, e.getMessage());
//            e.printStackTrace();
//        }
    }
}