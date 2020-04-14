package com.yb.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ServiceBindSample extends Service {

    LocalBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ServiceBindSample", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ServiceBindSample", "onStartCommand" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("ServiceBindSample", "onbind");
        if (mBinder == null) {
            mBinder = new LocalBinder();
        }
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("ServiceBindSample", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("ServiceBindSample", "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("ServiceBindSample", "onDestroy");
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        ServiceBindSample getService() {
            return ServiceBindSample.this;
        }
    }
}
