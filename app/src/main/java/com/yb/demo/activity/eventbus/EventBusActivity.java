package com.yb.demo.activity.eventbus;

import android.os.Bundle;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

public class EventBusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_bus;
    }

    @Override
    protected void initView() {
        initCommonView();
    }

    @Override
    protected void initData() {
        mTvTopBarTitle.setText("EventBus");
    }

    @Override
    protected void initListener() {

    }
}
