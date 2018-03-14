package com.yb.demo.application;

import android.app.Application;

import com.base.baselibrary.config.AppConfig;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by yb on 2017/12/27.
 */
public class YbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        initMemmoryCheck();
    }

    private void initConfig() {
        //全局配置
        AppConfig.init(this)
                .withLogSwitch(true)
                .withHost("http://127.0.0.1")
                .withCachePublicTemp("")
                .withCachePublicImage("")
                .withCachePrivateImage("")
                .build();

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
