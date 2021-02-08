package com.project.network;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.library.network.HttpHelper;
import com.library.network.bean.ErrMsg;
import com.library.network.callback.HttpCallback;
import com.project.network.callback.WanAndroidCallback;
import com.project.network.bean.Banner;
import com.project.network.constant.Constant;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        test();
//        test2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length == 2) {

        }
    }

    private void test() {
        HttpHelper.getInstance().get(Constant.URL_BANNER, null, new HttpCallback<List<Banner>>() {

            @Override
            public void onSuccess(List<Banner> data) {
                Log.i("TAG", "onSuccess: " + data.toString());
            }

            @Override
            public void onFailure(ErrMsg errMsg) {

            }
        });
    }

    private void test2() {
        HttpHelper.getInstance().get(Constant.URL_BANNER, null, new WanAndroidCallback<List<Banner>>() {

            @Override
            public void onSuccess(List<Banner> data) {
                Log.i("TAG", "onSuccess: " + data.toString());
            }

            @Override
            public void onFailure(ErrMsg errMsg) {

            }
        });
    }
}