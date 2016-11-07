package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.mulitpart.MulitEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longquan on 2015/12/12.
 */
public class MultiPartReqeust extends Request<String> {

    private Response.Listener succesListener;
    private ResponseDelivery delivery;

    private MulitEntity mulitEntity;
    private HashMap<String, String> headers;

    public MultiPartReqeust(int method, String url, Response.ErrorListener errorListener, Response.Listener succesListener) {
        super(method, url, errorListener);
        this.succesListener = succesListener;
        this.headers = new HashMap<>();
    }


    public void setDelivery(ResponseDelivery delivery) {
        this.delivery = delivery;
    }

    public void setMulitEntity(MulitEntity mulitEntity) {
        this.mulitEntity = mulitEntity;
        this.mulitEntity.setContentType(headers);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        this.succesListener.onResponse(response);
    }


    public void writeMulitPartEntity(OutputStream out) throws IOException {

        ProcessOutputStream outputStream = new ProcessOutputStream(out, mulitEntity.contentLenth(), new ProcessOutputStream.onWriteListener() {
            @Override
            public void onWrite(long current, long total) {
                if (delivery != null) {
                    delivery.onPosting(MultiPartReqeust.this, current, total);
                }
            }
        });
        if (mulitEntity != null)
            mulitEntity.wirteTo(outputStream);
    }


}
