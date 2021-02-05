package com.project.network.callback;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class WanAndroidCallback<T> extends BaseHttpCallback<T> {

    private int code = -1;
    private String msg = "";
    private String data = "";

    @Override
    public void parseResult(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            code = jsonObject.has("errorCode") ? jsonObject.getInt("errorCode") : -1;
            msg = jsonObject.has("errorMsg") ? jsonObject.getString("errorMsg") : "";
            data = jsonObject.has("data") ? jsonObject.getString("data") : "";
        } catch (JSONException e) {
            //
        }
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public boolean isSucceed() {
        return this.code == 0;
    }
}