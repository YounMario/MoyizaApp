package com.younchen.chat.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.younchen.chat.R;
import com.younchen.chat.model.LoginModel;
import com.younchen.chat.utils.ToastUtil;

import org.simple.eventbus.EventBus;


public class LoginActivity extends BaseActivity {

    private EditText editUserName;
    private EditText editPassWord;

    private String TOAST_EMPTY_USERNAME;
    private String TOAST_EMPTY_PASSWORD;
    private String TOAST_PASSWORD_OR_USERNAME_ERROR;

    private LoginModel loginModel;

    private EventBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initEvent();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        editUserName = (EditText) findViewById(R.id.edit_username);
        editPassWord = (EditText) findViewById(R.id.edit_password);
    }

    @Override
    public void initEvent() {
//        aliModel = new AliModel();
//        aliModel.init(this);

    }

    @Override
    public void initData() {
        TOAST_EMPTY_USERNAME = getResources().getString(R.string.toast_username_can_not_empty);
        TOAST_EMPTY_PASSWORD = getResources().getString(R.string.toast_pass_word_can_not_empty);
        TOAST_PASSWORD_OR_USERNAME_ERROR = getResources().getString(R.string.toast_pass_word_or_user_name_error);
        bus = EventBus.getDefault();
        bus.register(this);
        loginModel = new LoginModel(this);
    }

    public void login(View v) {
//        String userName = editUserName.getText().toString();
//        String passWord = editPassWord.getText().toString();

        String userName = "13167390434";
        String passWord = "123456";
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.show(TOAST_EMPTY_USERNAME);
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            ToastUtil.show(TOAST_EMPTY_PASSWORD);
            return;
        } else {
            loginModel.requestLogin(userName, passWord);
        }
    }

    public void sign(View v) {

        goToActivity(SignActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
