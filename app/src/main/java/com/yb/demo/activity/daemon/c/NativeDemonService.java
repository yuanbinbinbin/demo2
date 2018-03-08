package com.yb.demo.activity.daemon.c;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.base.baselibrary.jni.daemon.NativeDaemonUtil;
import com.yb.demo.utils.ToastUtil;

public class NativeDemonService extends Service {


    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ToastUtil.showShortTime(getApplicationContext(), "Live " + Thread.currentThread().getName());
            mHandler.postDelayed(runnable, 5000L);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final String path = Environment.getExternalStoragePublicDirectory("/._sys_listen").getAbsolutePath();
        Log.e("test", "NativeDaemon path: " + path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        String packagePath = getFilesDir().getAbsolutePath();
        Log.e("test", "NativeDaemon package Path: " + packagePath);
        NativeDaemonUtil.start(getPackageName() + "/" + getClass().getName(), path, packagePath, "http://www.baidu.com");
        mHandler.postDelayed(runnable, 5000L);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test", "NativeDaemon onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
