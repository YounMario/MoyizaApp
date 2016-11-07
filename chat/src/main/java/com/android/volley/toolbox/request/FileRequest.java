package com.android.volley.toolbox.request;

import android.os.Environment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/15.
 */
public class FileRequest<T extends File> extends Request<File> {

    private Response.ErrorListener errorLisener;
    private Response.Listener successListener;
    private HashMap<String, String> params;
    private HashMap<String, String> header;
    private String path;


    public FileRequest(int method, String url, HashMap<String, String> params, Response.Listener successListener, Response.ErrorListener errorLisener) {
        super(method, url, errorLisener);
        this.errorLisener = errorLisener;
        this.successListener = successListener;
        this.params = params;
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

    public FileRequest setDownLoadProcess(PrograssListener listener) {
        mGetProcessListener = listener;
        return this;
    }

    public FileRequest setSavePath(String path) {
        this.path = path;
        return this;
    }

    public FileRequest setHeader(HashMap<String, String> header) {
        this.header = header;
        return this;
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            byte[] data = response.data;
            File file = null;
            if (path != null) {
                file = new File(path);
            } else {
                if (Environment.getDataDirectory().canRead()) {
                    String cache = Environment.getDataDirectory().getAbsolutePath() + "/files";
                    File dir = new File(cache);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    StringBuffer filePath = new StringBuffer();
                    filePath.append(dir.getPath());
                    filePath.append(File.separator);
                    filePath.append(new Date().getTime());
                    file = new File(filePath.toString());
                }

            }
            if (file == null) {
                throw new RuntimeException("fail to create save path");
            }
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(data);
            return Response.success(file, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }

    @Override
    protected void deliverResponse(File file) {
        this.successListener.onResponse(file);
    }

    @Override
    public String getUrl() {
        String url = super.getUrl();
        if (getMethod() == Method.GET) {
            url += httpGetParam();
        }
        return url;
    }


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
