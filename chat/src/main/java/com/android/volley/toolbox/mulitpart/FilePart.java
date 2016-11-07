package com.android.volley.toolbox.mulitpart;


import com.android.volley.toolbox.ProcessOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * author younchen
 */
public class FilePart extends BasePart {

    private File file;
    private String key;


    public FilePart(String key, File file) {
        this.file = file;
        this.key = key;
        final String paramName = UrlEncodingUtils.encode(key, null);
        final String fileName = UrlEncodingUtils.encode(file.getName(), null);

        headersProvider = new IHeadersProvider() {
            @Override
            public String getContentDisposition() {
                return "Content-Disposition: form-data; name=\"" + paramName +
                        "\"; filename=\"" + fileName + '"';
            }

            @Override
            public String getContentType() {
                return "Content-Type: application/octet-stream";
            }

            @Override
            public String getContentTransferEncoding() {
                return "Content-Transfer-Encoding: binary";
            }
        };
    }

    public File getFile() {
        return file;
    }


    @Override
    public long getContentLength(Boundary boundary) {
        return file.length() + getHeader(boundary).length + CRLF.length;
    }

    @Override
    public void writeTo(ProcessOutputStream out, Boundary boundary) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        byte[] discription = getHeader(boundary);
        byte[] buf = new byte[1024];
        int count = 0;
        out.write(discription);
        try {
            while ((count = fin.read(buf)) != -1) {
                out.write(buf, 0, count);
            }
            out.write(CRLF);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            fin.close();
        }
    }
}
