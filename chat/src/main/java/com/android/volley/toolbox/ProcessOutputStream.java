package com.android.volley.toolbox;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by longquan on 2015/12/12.
 */
public class ProcessOutputStream {

    private long contentLenth;
    private OutputStream out;
    private onWriteListener listener;
    private long current;

    private long index;

    public ProcessOutputStream(OutputStream out, long contentLenth, onWriteListener listener) {
        this.out = out;
        this.listener = listener;
        this.contentLenth = contentLenth;
        index = 0;
    }


    public void write(byte[] buffer, int offset, int count) throws IOException {
        out.write(buffer, offset, count);
        current += count;
        updateProcess(count);
    }

    public void write(byte[] buffer) throws IOException {
        out.write(buffer);
        current += buffer.length;
        updateProcess(buffer.length);

    }


    private void updateProcess(long count) {
        if (listener != null) {

            if (needProcess(count)) {
                listener.onWrite(current, contentLenth);
            }
        }
    }

    /**
     * 当写入的流大小 大于 文件流的总大小的 百分之一返回true
     *
     * @return
     */
    private boolean needProcess(long count) {
        index += count;
        if (index > contentLenth / 100 || current == contentLenth) {
            index = 0;
            return true;
        }
        return false;
    }


    public void flush() throws IOException {
        out.flush();
    }

    public interface onWriteListener {
        public void onWrite(long current, long total);
    }
}