package com.android.volley.toolbox.mulitpart;

import com.android.volley.toolbox.ProcessOutputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by younchen on 2015/12/12.
 */
public class StringPart extends BasePart {

    private final byte[] valueBytes;

    public StringPart(String name, String value, String charset) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");     //$NON-NLS-1$
        }
        if (value == null) {
            throw new IllegalArgumentException("Value may not be null");    //$NON-NLS-1$
        }

        final String partName = UrlEncodingUtils.encode(name, null);

        if (charset == null) {
            charset = "utf-8";
        }
        final String partCharset = charset;

        try {
            this.valueBytes = value.getBytes(partCharset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        headersProvider = new IHeadersProvider() {
            public String getContentDisposition() {
                return "Content-Disposition: form-data; name=\"" + partName + '"'; //$NON-NLS-1$
            }

            public String getContentType() {
                return "Content-Type: " + "text/plain; charset=" + partCharset;  //$NON-NLS-1$
            }

            public String getContentTransferEncoding() {
                return "Content-Transfer-Encoding: 8bit";  //$NON-NLS-1$
            }
        };
    }

    /**
     * 默认是utf-8
     *
     * @param name
     * @param value
     */
    public StringPart(String name, String value) {
        this(name, value, null);
    }

    public long getContentLength(Boundary boundary) {
        return getHeader(boundary).length + valueBytes.length + CRLF.length;
    }

    @Override
    public void writeTo(ProcessOutputStream out, Boundary boundary) throws IOException {
        out.write(getHeader(boundary));
        out.write(valueBytes);
        out.write(CRLF);
    }
}
