package com.library.network.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {

    /**
     * 将Json数据解析成映射对象
     */
    public static <T> T parseJsonObject(String json, Class<T> clazz) {
        try {
            if (TextUtils.isEmpty(json)) {
                return null;
            }
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            Log.i("TAG", "JsonSyntaxException: " + e.getMessage());
            return null;
        }
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     */
    public static <T> List<T> parseJsonArray(String json, Class<T> clazz) {
        try {
            if (TextUtils.isEmpty(json)) {
                return new ArrayList<>();
            }
            Type type = new TypeToken<List<JsonObject>>() {
            }.getType();
            List<JsonObject> jsonObjects = new Gson().fromJson(json, type);
            List<T> arrayList = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
            return arrayList;
        } catch (JsonSyntaxException e) {
            Log.i("TAG", "JsonSyntaxException: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 判断json数据类型
     */
    private void getJsonType(String json) {
        try {
            Object dataJson = new JSONTokener(json).nextValue();
            if (dataJson instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) dataJson;
            } else if (dataJson instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) dataJson;
            } else {
                //
            }
        } catch (JSONException e) {
            Log.i("TAG", "JSONException: " + e.getMessage());
        }
    }
}