package com.younchen.chat.entity.result;

import com.younchen.chat.entity.User;

/**
 * Created by Administrator on 2015/10/22.
 */
public class UserResult extends Result {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
