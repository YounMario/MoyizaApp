package com.android.volley.toolbox;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by longquan on 2015/12/15.
 */
public class ProcessInputStream {

    private long contentLenth;
    private InputStream in;
    private onReadListener listener;
    private long current;

    private long index;

    public ProcessInputStream(InputStream in, long contentLenth, onReadListener listener) {
        this.in = in;
        this.listener = listener;
        this.contentLenth = contentLenth;
        index = 0;
    }


    public int read(byte[] buffer, int offset, int count) throws IOException {
        int readCount = in.read(buffer, offset, count);
        current += count;
        updateProcess(count);
        return readCount;
    }

    public int read(byte[] buffer) throws IOException {
        int readCount = in.read(buffer);
        current += readCount;
        updateProcess(buffer.length);
        return readCount;
    }


    private void updateProcess(long count) {
        if (listener != null) {
            if (needProcess(count)) {
                listener.onRead(current, contentLenth);
            }
        }
    }

    private boolean needProcess(long count) {
        index += count;
        if (index > contentLenth / 100 || current == contentLenth) {
            index = 0;
            return true;
        }
        return false;
    }


    public interface onReadListener {
        public void onRead(long current, long total);
    }
}
