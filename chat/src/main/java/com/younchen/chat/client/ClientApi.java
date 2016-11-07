package com.younchen.chat.client;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpManager;
import com.android.volley.toolbox.request.GsonRequest;
import com.younchen.chat.entity.result.LoginResult;
import com.younchen.chat.entity.result.Result;
import com.younchen.chat.entity.result.UsersResult;

/**
 * Created by Administrator on 2015/10/8.
 */
public class ClientApi {
    private static final String HOST = "http://192.168.1.102:8080/Moyiza";


    public static void login(String phone, String password, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = HOST + "/User/login";
        Request request = new GsonRequest<LoginResult>(LoginResult.class, Request.Method.POST, url, errorListener, listener).addParam("phone", phone).addParam("password", password);
        HttpManager.getInstance().addToRequestQueue(request, "login");
    }

    public static void regist(String password, String phone, String code, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = HOST + "/User/regist";
        Request request = new GsonRequest<Result>(Result.class, Request.Method.POST, url, errorListener, listener).addParam("password", password).addParam("phone", phone).addParam("code", code);
        HttpManager.getInstance().addToRequestQueue(request, "regist");
    }

    //
    public static void getUsers(Integer page, Integer count, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = HOST + "/User/users/{page}/{size}".replace("{page}", page.toString()).replace("{size}", count.toString());
        Request request = new GsonRequest(UsersResult.class, Request.Method.GET, url, errorListener, listener);
        HttpManager.getInstance().addToRequestQueue(request, "getUsers");
    }
//
//    public static void getAliToken(Response.Listener listener, Response.ErrorListener errorListener) {
//        String url = HOST + "/Ali/token";
//        HttpRequest request = new HttpRequestBuilder().url(url).method(HttpRequestBuilder.HttpMethod.GET).build();
//        HttpUtil.getInstance().sendRequest(request, callBack);
//    }
}
