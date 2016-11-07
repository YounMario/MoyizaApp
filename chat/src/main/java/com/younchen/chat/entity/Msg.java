package com.younchen.chat.entity;

/**
 * Created by sky on 2015/11/11.
 */
public class Msg  {
    //判断接收消息还是发送消息
    public static final int IS_SEND=1;
    public static final int IS_RECEIVE=2;

    public static final int MSG_TXT=3;
    public  static final int MSG_VOICE=4;
    public  static final int MSG_IMG=5;

    private int type;
    private String name;
    private String userImage;
    private int direct;
    public int layout;


    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getType() {
        return type;
    }



    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
