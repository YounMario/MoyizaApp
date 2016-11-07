package com.android.volley.toolbox;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by younchen on 2015/12/27.
 */
public class HttpEntity {
    private String contentEncoding;
    private String contentType;
    private InputStream content;
    private int contentLength;

    public void setContent(InputStream content) {
        this.content = content;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public InputStream getContent() {
        return content;
    }

    public void consumeContent() throws IOException {
        if (content != null)
            content.close();

    }
}
