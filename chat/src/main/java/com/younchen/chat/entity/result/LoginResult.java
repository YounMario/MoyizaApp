package com.younchen.chat.entity.result;

import com.younchen.chat.entity.User;

/**
 * Created by Administrator on 2015/10/23.
 */
public class LoginResult extends Result {

    private LoginDate data;

    public LoginDate getData() {
        return data;
    }

    public void setData(LoginDate data) {
        this.data = data;
    }


    public class LoginDate {

        private String token;
        private String session;
        private User user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSession() {
            return session;
        }

        public void Session(String session) {
            this.session = session;
        }

        ;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}
