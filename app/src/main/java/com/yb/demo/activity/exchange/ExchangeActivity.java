package com.yb.demo.activity.exchange;

import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.activity.exchange.meituan1.MeiTuanMainActivity1;
import com.yb.demo.utils.ActivityUtil;

public class ExchangeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange;
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

    public void exchange1(View v) {
        ActivityUtil.startActivity(this, MeiTuanMainActivity1.class);
    }
}
