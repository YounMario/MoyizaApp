package com.younchen.chat.ui.controller;

import android.content.Context;
import android.view.View;

/**
 * Created by pc on 2016/3/23.
 */
public abstract class BaseController {

    protected View root;

    public BaseController(View root) {
        this.root = root;
        initView();
        initData();
        initEvent();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();
}
