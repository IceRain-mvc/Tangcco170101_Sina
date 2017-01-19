package com.tangcco.android.TangccoAndroid030_31.volley_util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class MyJsonObjectRequest extends Request<JSONObject> {
    private Map<String, String> mMap;
    private Response.Listener<JSONObject> mListener;

    public MyJsonObjectRequest(String url, Response.Listener<JSONObject> successListener,Response.ErrorListener errorListener, Map<String, String> mMap) {
        super(Method.POST, url, errorListener);
        this.mMap = mMap;
        this.mListener = successListener;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        mListener.onResponse(jsonObject);
    }

}
