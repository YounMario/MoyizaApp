package com.android.volley.toolbox.mulitpart;

import com.android.volley.toolbox.ProcessOutputStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by younchen on 2015/12/12.
 */
public class MulitEntity implements Serializable {

    private ArrayList<BasePart> parts;
    private Boundary boundary;

    public MulitEntity() {
        parts = new ArrayList<>();
        boundary = new Boundary(null);
    }

    public void addEntity(BasePart part) {
        parts.add(part);
    }

    public long contentLenth() {
        int count = 0;
        for (BasePart part :
                parts) {
            count += part.getContentLength(boundary);
        }
        return count;
    }

    public void wirteTo(ProcessOutputStream out) throws IOException {
        for (BasePart part :
                parts) {
            part.writeTo(out, boundary);
        }
        out.write(boundary.getClosingBoundary());
        out.flush();
    }

    public void setContentType(HashMap<String, String> header) {
        header.put("Content-Type", "multipart/form-data; boundary=" + boundary.getBoundary());
    }
}
