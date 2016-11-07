package com.younchen.chat.entity.result;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/18.
 */
public class Result implements Serializable {

    private int code;
    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
