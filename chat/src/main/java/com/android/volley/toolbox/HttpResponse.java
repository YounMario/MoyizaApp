package com.android.volley.toolbox;

import java.util.Map;

/**
 * Created by younchen on 2015/12/27.
 */
public class HttpResponse {
    private int responseCode;
    private String responseMessage;
    private Map<String, String> headers;
    private HttpEntity entity;

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return responseCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }

    public HttpEntity getEntity() {
        return entity;
    }
}
