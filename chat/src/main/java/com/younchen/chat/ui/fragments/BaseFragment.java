package com.younchen.chat.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.chat.App;
import com.younchen.chat.entity.User;
import com.younchen.chat.ui.BaseActivity;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity parentActivity;

    private String tag = "MOYIZA";

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 初始化 数据
     */
    protected abstract void initDate();

    protected abstract void initEvent();

    protected abstract void initView(View view);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (BaseActivity) activity;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public App getAppContext() {
        return (App) parentActivity.getApplication();
    }

    public User getMyInfo() {
        return this.getAppContext().getLoginDate().getUser();
    }


    public void goToActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtra("params", bundle);
        getActivity().startActivity(intent);
    }

    public void d(String d) {
        Log.d(tag, d);
    }

    public void e(String e) {
        Log.d(tag, e);
    }

    public void i(String i) {
        Log.d(tag, i);
    }

    public boolean onToucnEvent(MotionEvent event) {
        return false;
    }

    ;

}
