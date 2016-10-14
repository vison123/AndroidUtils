package com.vison.androidutils.network.okhttp;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 *
 * Created by vison on 16/7/6.
 */
public class FasHttp {
    protected static Map<String, String> headers = new LinkedHashMap<>();

    static {
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Basic  ");
    }


    protected static void paramHttp(String url, Map params, Callback callback) {
        Logger.d("请求地址: " + url);
        Logger.d("请求参数: " + params.toString());
        OkHttpUtils
                .post()
                .url(url)
                .params(params)
                .headers(headers)
                .build()
                .execute(callback);
    }

    //body由json转化为string传进来
    protected static void bodyHttp(String url, String body, Callback callback) {
        Logger.d("请求地址: " + url);
        Logger.d("请求参数: " + body);
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        OkHttpUtils
                .postString()
                .url(url)
                .content(body)
                .headers(headers)
                .mediaType(mediaType)
                .build()
                .execute(callback);
    }

    protected static void getHttp(String url, Map params, Callback callback) {
        Logger.d("请求地址: ", url);
        Logger.d("请求参数: ", params.toString());
        OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .headers(headers)
                .build()
                .execute(callback);
    }

}
