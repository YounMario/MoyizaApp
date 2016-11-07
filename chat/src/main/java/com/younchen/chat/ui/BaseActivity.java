package com.younchen.chat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.younchen.chat.App;
import com.younchen.chat.R;
import com.younchen.chat.entity.User;
import com.younchen.chat.utils.ViewFindUtils;

public abstract class BaseActivity extends AppCompatActivity {

    protected View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
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

    public void goToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }


    public void goToActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null)
            intent.putExtra("params", bundle);
        startActivity(intent);
    }

    public Bundle getParams() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getBundleExtra("params");
        }
        return null;
    }

    public void getRootView() {
        rootView = getWindow().getDecorView();
    }

    public <T extends View> T findView(int id) {
        return ViewFindUtils.find(rootView, id);
    }

    public boolean empty(String str) {
        return TextUtils.isEmpty(str);
    }

    public App getAppContext() {
        return (App) getApplication();
    }

    public User getMyInfo() {
        return this.getAppContext().getLoginDate().getUser();
    }

    public abstract void initView();

    public abstract void initEvent();

    public abstract void initData();

}
