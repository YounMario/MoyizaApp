package com.younchen.chat.entity.result;

import com.younchen.chat.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class UsersResult extends Result {

    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public class UserData {

        private List<User> users;

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }
}
