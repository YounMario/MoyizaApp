package com.android.volley.toolbox.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by longquan on 2015/12/15.
 */
public class GsonRequest<T> extends Request<T> {


    private HashMap<String, String> params;
    private HashMap<String, String> header;
    private Gson gson;
    private Class<?> clazz;

    private Response.Listener<T> mSuccesListener;


    public GsonRequest(Class<?> clazz, int method, String url, Response.ErrorListener errorListener, Response.Listener<T> successListener) {
        super(method, url, errorListener);
        params = new HashMap<>();
        header = new HashMap<>();
        gson = new Gson();
        mSuccesListener = successListener;
        this.clazz = clazz;
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        Object obj = gson.fromJson(parsed, clazz);
        return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccesListener.onResponse(response);
    }

    public GsonRequest addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    public GsonRequest addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    @Override
    public HashMap<String, String> getParams() {
        return params;
    }
}
