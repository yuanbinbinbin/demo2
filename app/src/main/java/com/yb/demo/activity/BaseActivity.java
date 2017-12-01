package com.yb.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.weights.ResultListView;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    // 通用顶部操作栏中的一些View
    protected ImageView mIvTopBarBack;
    protected TextView mTvTopBarTitle;
    protected ImageView mIvTopBarRight1;
    protected ImageView mIvTopBarRight2;
    protected TextView mTvTopBarRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected void initCommonView() {
        mIvTopBarBack = (ImageView) findViewById(R.id.id_common_title_bar_back);
        mTvTopBarTitle = (TextView) findViewById(R.id.id_common_title_bar_title);
        mIvTopBarRight1 = (ImageView) findViewById(R.id.id_common_title_bar_right1);
        mIvTopBarRight2 = (ImageView) findViewById(R.id.id_common_title_bar_right2);
        mTvTopBarRightText = (TextView) findViewById(R.id.id_common_title_bar_right_text);
        Handler handler = new Handler();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {

    }

    protected Context getContext() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
