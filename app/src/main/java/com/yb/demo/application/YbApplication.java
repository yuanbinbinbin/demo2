package com.yb.demo.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.base.baselibrary.config.AppConfig;
import com.base.baselibrary.net.OkHttpUtils;
import com.base.baselibrary.net.https.HttpsUtils;
import com.base.baselibrary.net.interceptor.LoggerInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.yb.demo.BuildConfig;
import com.yb.demo.lib.net.HeaderInterceptor;
import com.zhuge.analysis.stat.ZhugeSDK;

import java.net.Proxy;

import okhttp3.OkHttpClient;

/**
 * Created by yb on 2017/12/27.
 */
public class YbApplication extends DefaultApplicationLike {

    public YbApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplication(), "84d9fb10ff", BuildConfig.DEBUG);
        initConfig();
        initNet();
        initMemmoryCheck();
        initZhuGe();
    }

    private void initZhuGe() {
        //初始化分析跟踪
        ZhugeSDK.getInstance().init(getApplication());
        ZhugeSDK.getInstance().openDebug();
    }

    private void initConfig() {
        //全局配置
        AppConfig.init(getApplication())
                .withLogSwitch(true)
                .withHost("http://127.0.0.1")
                .withCachePublicTemp("")
                .withCachePublicImage("")
                .withCachePrivateImage("")
                .build();

    }

    private void initMemmoryCheck() {
        if (LeakCanary.isInAnalyzerProcess(getApplication())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());
    }

    private void initNet() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        //region 设置可访问所有的https网站
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        clientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);//设置可访问所有的https网站
        //endregion

        //region log
        clientBuilder.addInterceptor(new LoggerInterceptor("demo"));
        //endregion

        //region 通用header
        clientBuilder.addInterceptor(new HeaderInterceptor());
        //endregion

        //region防止抓包
        if (!BuildConfig.DEBUG) {
            clientBuilder.proxy(Proxy.NO_PROXY);
            System.getProperties().remove("http.proxyHost");
            System.getProperties().remove("http.proxyPort");
            System.getProperties().remove("https.proxyHost");
            System.getProperties().remove("https.proxyPort");
        }
        //endregion

        OkHttpUtils.initClient(clientBuilder.build())
                .defaultHost("https://www.baidu.com");
    }

}
