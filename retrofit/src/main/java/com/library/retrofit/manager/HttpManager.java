
package com.library.retrofit.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.library.retrofit.RetrofitClient;
import com.library.retrofit.callback.FileResponse;
import com.library.retrofit.callback.HttpCallback;
import com.library.retrofit.bean.DownloadInfo;
import com.library.retrofit.interceptor.OffLineIntercept;
import com.library.retrofit.interceptor.UploadInterceptor;
import com.library.retrofit.listener.DownloadListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class HttpManager {

    public static Context mContext;
    private volatile static HttpManager httpManager;
    private static int HANDER_DELAYED_TIME = 500;
    /**
     * 这个headers是每次请求动态更新的，用完需要清掉
     */
    private static Map<String, String> tmpHeaders;
    private static String baseUrl = "";
    private static String tmpBaseUrl = "";
    private static OnGetHeadersListener onGetHeadersListener;
    private static String cacheDirPath;
    private static long maxCacheSize;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (mContext == null) {
            Log.e("HttpManager", "please call the method of init() first");
        }
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }
        return httpManager;
    }

    public interface OnGetHeadersListener {
        Map<String, String> getHeaders();
    }

    public static void setOnGetHeadersListener(OnGetHeadersListener onGetHeadersListener) {
        HttpManager.onGetHeadersListener = onGetHeadersListener;
    }

    /**
     * 初始化信息
     *
     * @param context 上下文
     * @param baseUrl URL前缀
     */
    public static void init(Context context, String baseUrl) {
        HttpManager.mContext = context;
        setBaseUrl(baseUrl);
    }

    /**
     * 设置缓存路径和最大缓存大小
     *
     * @param cacheDirPath 缓存路径
     * @param maxSize      最大缓存大小
     */
    public static void initCache(String cacheDirPath, long maxSize) {
        if (TextUtils.isEmpty(cacheDirPath) || maxSize <= 0) {
            return;
        }
        if (!new File(cacheDirPath).exists()) {
            new File(cacheDirPath).mkdirs();
        }

        HttpManager.cacheDirPath = cacheDirPath;
        HttpManager.maxCacheSize = maxSize;
    }

    /**
     * 给Retrofit添加拦截器，设置链接前缀
     */
    public static RetrofitClient.Builder getRetrofitBuilder(String baseUrl) {
        RetrofitClient.Builder builder = new RetrofitClient.Builder()
//                .addGsonConverterInterceptor(GsonConverterFactory.create())
                .addRxJavaCallAdapterInterceptor(RxJavaCallAdapterFactory.create())
//                .addRequestJsonInterceptor(RequestJsonInterceptor.create())
                .addOffLineIntercept(OffLineIntercept.create(mContext))
                .isLog(true);

        handleHeaders(builder);

        if (!TextUtils.isEmpty(tmpBaseUrl)) {
            builder.baseUrl(tmpBaseUrl);
            tmpBaseUrl = "";
        } else {
            if (!TextUtils.isEmpty(baseUrl)) {
                builder.baseUrl(baseUrl);
            }
        }

        return builder;
    }

    private static void handleHeaders(RetrofitClient.Builder builder) {
        Map<String, String> headerMap = new HashMap<>();
        if (onGetHeadersListener != null) {
            Map<String, String> listenerHeaders = onGetHeadersListener.getHeaders();
            if (listenerHeaders != null) {
                headerMap.putAll(listenerHeaders);
            }
        }
        //动态添加的headers的优先级要高于固定的onGetHeadersListener中的
        if (tmpHeaders != null && tmpHeaders.size() > 0) {
            headerMap.putAll(tmpHeaders);
            //这个headers是每次请求动态更新的，所以用完需要清掉
            tmpHeaders.clear();
        }
//        if (headerMap.get("Content-Type") == null) {
//            headerMap.put("Content-Type", "application/json; charset=utf-8");
//        }
//        if (headerMap.get("Accept") == null) {
//            headerMap.put("Accept", "application/json");
//        }
        builder.addHeader(headerMap);
    }

    /**
     * 初始化头部信息，添加一些共同的请求头参数
     */
    public static void addTmpHeaders(Map<String, String> headers) {
        tmpHeaders = headers;
    }

    public static void setTmpBaseUrl(String tmpBaseUrl) {
        HttpManager.tmpBaseUrl = tmpBaseUrl;
    }

    public static Map<String, String> getTmpHeaders() {
        return tmpHeaders;
    }

    /**
     * 设置共同URL前缀
     */
    public static void setBaseUrl(String baseUrl) {
        HttpManager.baseUrl = baseUrl;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 发送Get请求
     *
     * @param url        请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param parameters 请求参数
     * @param callback   网络回调
     */
    public static void get(String url, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().get(mContext, url, parameters, callback);
    }

    /**
     * 同步Get
     */
    public static void syncGet(String url, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().syncGet(mContext, url, parameters, callback);
    }

    /**
     * 发送Get请求
     *
     * @param fullUrl    请求绝对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param parameters 请求参数
     * @param callback   网络回调
     */
    public static void getFullPath(String fullUrl, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().getFullPath(mContext, fullUrl, parameters, callback);
    }

    /**
     * 发送Post请求
     *
     * @param url        请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param parameters 请求参数
     * @param callback   网络回调
     */
    public static void post(String url, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().post(mContext, url, parameters, callback);
    }

    /**
     * 发送Put请求
     *
     * @param url      请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param body     请求体
     * @param callback 网络回调
     */
    public static <T> void putByBody(String url, T body, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().putByBody(mContext, url, body, callback);
    }

    /**
     * 发送Put请求
     *
     * @param url        请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param parameters 请求参数
     * @param callback   网络回调
     */
    public static void put(String url, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().put(mContext, url, parameters, callback);
    }

    /**
     * 发送DELETE请求
     *
     * @param url      请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param callback 网络回调
     */
    public static void delete(String url, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().delete(mContext, url, parameters, callback);
    }

    /**
     * 发送DELETE请求
     *
     * @param url      请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param callback 网络回调
     */
    public static void delete(String url, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().delete(mContext, url, callback);
    }

    /**
     * 发送DELETE请求
     *
     * @param url      请求相对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param body     请求参数
     * @param callback 网络回调
     */
    public static <T> void deleteByBody(String url, T body, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().deleteByBody(mContext, url, body, callback);
    }

    /**
     * 同步Post
     */
    public static void syncPost(String url, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().syncPost(mContext, url, parameters, callback);
    }

    /**
     * 注意，此方法传到服务器的是一个json串
     */
    public static <T> void postByBody(String url, T body, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().postByBody(mContext, url, body, callback);
    }

    /**
     * 发送Post请求
     *
     * @param fullUrl    请求绝对地址，地址共同部分前缀在{@link #getRetrofitBuilder(String)}中设置
     * @param parameters 请求参数
     * @param callback   网络回调
     */
    public static void postFullPath(String fullUrl, Map<String, Object> parameters, HttpCallback callback) {
        getRetrofitBuilder(baseUrl).build().postFullPath(mContext, fullUrl, parameters, callback);
    }

    /**
     * 上传文件
     *
     * @param url                文件上传相对地址
     * @param filePath           本地文件路径
     * @param fileDes            文件描述
     * @param fileResponseResult 回调
     */
    public static void uploadFile(String url, String filePath, String fileDes, FileResponse fileResponseResult) {
        uploadFile(url, filePath, fileDes, false, fileResponseResult);
    }

    /**
     * 上传文件
     *
     * @param fullUrl            文件上传绝对地址
     * @param filePath           本地文件路径
     * @param fileDes            文件描述
     * @param fileResponseResult 回调
     */
    public static void uploadFileFullPath(String fullUrl, String filePath, String fileDes, FileResponse fileResponseResult) {
        uploadFile(fullUrl, filePath, fileDes, true, fileResponseResult);
    }

    private static void uploadFile(String url, String filePath, String fileDes, boolean useFullUrl, final FileResponse fileResponseResult) {
        getRetrofitBuilder(baseUrl)
                .addUploadFileInterceptor(UploadInterceptor.create())
                .build()
                .uploadFile(url, filePath, fileDes, useFullUrl, fileResponseResult);
    }

    /**
     * 上传文件
     *
     * @param url                文件上传相对地址
     * @param filePathList       本地文件路径
     * @param fileResponseResult 回调
     */
    public static void uploadFiles(String url, List<String> filePathList, FileResponse fileResponseResult) {
        uploadFiles(url, filePathList, false, fileResponseResult);
    }

    /**
     * 上传文件
     *
     * @param fullUrl            文件上传绝对地址
     * @param filePathList       本地文件路径
     * @param fileResponseResult 回调
     */
    public static void uploadFilesFullPath(String fullUrl, List<String> filePathList, FileResponse fileResponseResult) {
        uploadFiles(fullUrl, filePathList, true, fileResponseResult);
    }

    private static void uploadFiles(String url, List<String> filePathList, boolean useFullUrl, final FileResponse fileResponseResult) {
        getRetrofitBuilder(baseUrl)
                .addUploadFileInterceptor(UploadInterceptor.create())
                .build()
                .uploadFiles(url, filePathList, useFullUrl, fileResponseResult);
    }

    public static void download(String url, String savePath, DownloadListener listener) {
        DownloadInfo info = new DownloadInfo(url, savePath);
        info.setState(DownloadInfo.START);
        info.setListener(listener);
        DownloadManager downloadManager = DownloadManager.getInstance();

        downloadManager.startDown(info);
    }

    public static void download(DownloadInfo info) {
        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.startDown(info);
    }

    public DownloadManager getDownloadManager() {
        return DownloadManager.getInstance();
    }
}