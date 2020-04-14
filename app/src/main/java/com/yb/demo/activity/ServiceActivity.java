package com.yb.demo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.service.ServiceBindSample;
import com.yb.demo.service.ServiceSample;

public class ServiceActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
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


    public void startService(View v) {
        Intent intent = new Intent(this, ServiceSample.class);
        startService(intent);
    }

    public void stopService(View v) {
        Intent intent = new Intent(this, ServiceSample.class);
        stopService(intent);
    }


    public void bindService(View v) {
        Intent intent = new Intent(this, ServiceBindSample.class);
        bindService(intent, bindServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View v) {
        unbindService(bindServiceConnection);
    }

    private ServiceConnection bindServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ServiceActivity", "onServiceConnected : " + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ServiceActivity", "onServiceDisconnected");
        }
    };
}
