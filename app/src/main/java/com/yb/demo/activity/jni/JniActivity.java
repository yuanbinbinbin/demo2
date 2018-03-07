package com.yb.demo.activity.jni;

import android.view.View;

import com.base.baselibrary.jni.simple.Simple1;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.ToastUtil;

public class JniActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jni;
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

    public void simple1(View view){
        ToastUtil.showShortTime(getContext(), Simple1.sayHello("我是 java!"));
    }

    public void printLog(View view){
        Simple1.printLog("哈哈哈 我是Log 信息");
    }
}
