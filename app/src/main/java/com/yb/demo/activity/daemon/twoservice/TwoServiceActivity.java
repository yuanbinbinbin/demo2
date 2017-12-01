package com.yb.demo.activity.daemon.twoservice;

import android.app.Service;
import android.content.Intent;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

public class TwoServiceActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_two_service;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void start(View v) {
        startService(new Intent(getContext(), DaemonService1.class));
        startService(new Intent(getContext(), DaemonService2.class));
    }
}
