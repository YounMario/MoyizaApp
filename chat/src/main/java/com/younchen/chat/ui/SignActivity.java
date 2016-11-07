package com.younchen.chat.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.younchen.chat.R;
import com.younchen.chat.client.ClientApi;
import com.younchen.chat.entity.result.Result;
import com.younchen.chat.model.LoginModel;
import com.younchen.chat.utils.ToastUtil;

public class SignActivity extends BaseActivity {

    private EditText editPhone;
    private EditText editPassword;
    private EditText editPhoneCode;

    private LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();
        initData();
        initEvent();
        loginModel = new LoginModel(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        editPassword = (EditText) findViewById(R.id.edit_password);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editPhoneCode = (EditText) findViewById(R.id.edit_phone_code);

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    public void onSubmit(View v) {
        final String phone = editPhone.getText().toString();
        final String code = editPhone.getText().toString();
        final String password = editPhone.getText().toString();
        if (empty(phone)) {

        }
        if (empty(code)) {

        }
        if (empty(password)) {
        }
        ClientApi.regist(password, phone, code, new Response.Listener<Result>() {
            @Override
            public void onResponse(Result response) {
                ToastUtil.show(response.getMessage());
                loginModel.requestLogin(phone, password);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.show(error.getMessage());
            }
        });
    }
}
