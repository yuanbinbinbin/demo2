package com.yb.demo.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by yb on 2017/12/27.
 */
public class YbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initMemmoryCheck();
    }

    private void initMemmoryCheck() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
