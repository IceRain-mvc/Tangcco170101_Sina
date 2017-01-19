package com.tangcco.android.TangccoAndroid030_31.volley_util;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class MyStringRequest extends StringRequest {
    private Map<String,String> params;

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener,Map<String,String> params) {
        super(Method.POST, url, listener, errorListener);
        this.params = params;
    }

    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String str = null;
        try {
            str = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(str,
                HttpHeaderParser.parseCacheHeaders(response));

    }
}
