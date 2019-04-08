package com.yb.demo.application;

import android.app.Application;

import com.base.baselibrary.config.AppConfig;
import com.base.baselibrary.net.OkHttpUtils;
import com.base.baselibrary.net.https.HttpsUtils;
import com.base.baselibrary.net.interceptor.LoggerInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.yb.demo.BuildConfig;
import com.yb.demo.lib.net.HeaderInterceptor;
import com.zhuge.analysis.stat.ZhugeSDK;

import java.net.Proxy;

import okhttp3.OkHttpClient;

/**
 * Created by yb on 2017/12/27.
 */
public class YbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        initNet();
        initMemmoryCheck();
        initZhuGe();
    }

    private void initZhuGe() {
        //初始化分析跟踪
        ZhugeSDK.getInstance().init(getApplicationContext());
        ZhugeSDK.getInstance().openDebug();
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
