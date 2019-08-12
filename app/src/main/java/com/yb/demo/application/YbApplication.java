package com.yb.demo.application;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.base.baselibrary.config.AppConfig;
import com.base.baselibrary.net.OkHttpUtils;
import com.base.baselibrary.net.https.HttpsUtils;
import com.base.baselibrary.net.interceptor.LoggerInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
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
    public void registerActivityLifecycleCallback(final Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplication(), "84d9fb10ff", false);
        initConfig();
        initNet();
        initMemmoryCheck();
        initZhuGe();
        initUmeng();
    }

    private void initZhuGe() {
        //初始化分析跟踪
        ZhugeSDK.getInstance().init(getApplication());
        //ZhugeSDK.getInstance().openDebug();
    }

    private void initUmeng() {
        UMConfigure.init(getApplication(), "5cce83d9570df3c171000917", "DefaultChannel", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(getApplication(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 支持在子进程中统计自定义事件
        //UMConfigure.setProcessEvent(true);
        // 打开统计SDK调试模式
        //UMConfigure.setLogEnabled(true);
        //MobclickAgent.setDebugMode(true);
        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MobclickAgent.onResume(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                MobclickAgent.onPause(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
//        MobclickAgent.onEvent(getApplication(),"application start");
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
