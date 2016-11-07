package com.android.volley.toolbox.mulitpart;

;import com.android.volley.toolbox.ProcessOutputStream;

import java.io.IOException;

/**
 * Created by longquan on 2015/12/12.
 */
public interface Part {
    public long getContentLength(Boundary boundary);

    public void writeTo(ProcessOutputStream out, Boundary boundary) throws IOException;
}
