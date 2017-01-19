package com.tangcco.android.TangccoAndroid030_31.okhttp_util;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class OkHttpUtil {
    public static String http_post(String url, Map<String, String> params) {
        RequestBody body = null;
        FormEncodingBuilder formEncodingBuilder = null;
        try {
            if (params != null) {
                Set keys = params.keySet();
                Iterator<String> iterator = keys.iterator();

                formEncodingBuilder = new FormEncodingBuilder();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = params.get(key);
                    formEncodingBuilder.add(key, value);
                }
                body = formEncodingBuilder.build();
            } else {
                body = new FormEncodingBuilder().build();
            }
            String result = new OkHttpClient().newCall(new Request.Builder()
                    .url(url)
                    .post(body)
                    .build()).execute().body().string();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void http_post_asyn(String url, Map<String, String> params, Callback callback) {
        RequestBody body = null;
        FormEncodingBuilder formEncodingBuilder = null;
        if (params != null) {
            Set keys = params.keySet();
            Iterator<String> iterator = keys.iterator();

            formEncodingBuilder = new FormEncodingBuilder();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = params.get(key);
                formEncodingBuilder.add(key, value);
            }
            body = formEncodingBuilder.build();
        } else {
            body = new FormEncodingBuilder().build();
        }
        new OkHttpClient().newCall(new Request.Builder()
                .url(url)
                .post(body)
                .build()).enqueue(callback);
    }
}
