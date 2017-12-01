package com.yb.demo.activity.rx;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class RxAndroidActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_android;
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

    public void start1(View v) {
        createMethod1();
    }

    //第一种创建模式
    private void createMethod1() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });
        RxView.clicks(null).throttleFirst(500, TimeUnit.MILLISECONDS);
//        observable.compose()
    }
}
