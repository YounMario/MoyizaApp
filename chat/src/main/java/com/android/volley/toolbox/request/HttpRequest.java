package com.android.volley.toolbox.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rock on 2015/12/7.
 */
public class HttpRequest<T> extends Request<T> {

    private Response.ErrorListener errorLisener;
    private Response.Listener successListener;
    private HashMap<String, String> params;
    private HashMap<String, String> header;

    public HttpRequest(int method, String url, HashMap<String, String> params, Response.Listener successListener, Response.ErrorListener errorLisener) {
        super(method, url, errorLisener);
        this.errorLisener = errorLisener;
        this.successListener = successListener;
        this.params = params;
        this.header = header;
    }


    //post 自定义post请求body，默认实现是重写getParams， 在getBody中会调用 getParams拿到Map,然后
    //将Map写入requestBody 中,在getBody中实现加密即可
    @Override
    public byte[] getBody() throws AuthFailureError {
        //参数body 加密
        byte[] body = super.getBody();
        //返回加密后的body
        return new String(body).getBytes();
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String body = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(body, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        this.successListener.onResponse(response);
    }

    @Override
    public String getUrl() {
        String url = super.getUrl();
        if (getMethod() == Method.GET) {
            url += httpGetParam();
        }
        return url;
    }


    /**
     * get请求参数
     *
     * @return
     */
    private String httpGetParam() {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = this.params.entrySet().iterator();
            int i = 1;
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (i == 1) {
                    stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
                } else {
                    stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                }
                iterator.remove();
                i++;
            }
            return stringBuilder.toString();
        } else {
            return "";
        }
    }


}
