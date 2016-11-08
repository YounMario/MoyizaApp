package com.younchen.chat.model;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.younchen.chat.BuildConfig;
import com.younchen.chat.MainActivity;
import com.younchen.chat.client.ClientApi;
import com.younchen.chat.entity.result.LoginResult;
import com.younchen.chat.model.listener.RongMessageReciverListener;
import com.younchen.chat.ui.BaseActivity;
import com.younchen.chat.utils.DebugUtil;
import com.younchen.chat.utils.ToastUtil;

import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2015/10/25.
 */
public class LoginModel {

    private BaseActivity activity;
    private final String loginTag = "login";

    public LoginModel(BaseActivity activity) {
        this.activity = activity;
    }

    public void requestLogin(String phone, String password) {

        if(BuildConfig.DEBUG){
            activity.goToActivity(MainActivity.class);
            activity.finish();
        }else{
            ClientApi.login(phone, password, new Response.Listener<LoginResult>() {
                @Override
                public void onResponse(LoginResult response) {
                    if (response.getCode() == 0)
                        ToastUtil.show(response.getData().getSession());
                    connect(response.getData().getToken());
                    activity.getAppContext().setLoginDate(response.getData());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ToastUtil.show(error.getMessage());
                }
            });
        }
    }

    public void connect(String token) {
        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIMClient clinet = RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {

                Log.d("LoginActivity", "--onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {

                Log.d("LoginActivity", "--onSuccess" + userid);
                ToastUtil.show("link server successed");


                RongIMClient.setOnReceiveMessageListener(new RongMessageReciverListener(
                        activity.getAppContext()));
                activity.goToActivity(MainActivity.class);
                activity.finish();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("LoginActivity", "--onError" + errorCode);
            }
        });
    }
}
