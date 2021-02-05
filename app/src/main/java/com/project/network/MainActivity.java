package com.project.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.library.network.HttpHelper;
import com.library.network.bean.ErrMsg;
import com.library.network.callback.HttpCallback;
import com.project.network.callback.WanAndroidCallback;
import com.project.network.bean.Banner;
import com.project.network.constant.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        test();
        test2();
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