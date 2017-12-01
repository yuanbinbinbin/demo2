package com.yb.demo.activity.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.ActivityUtil;

public class AnimationActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animation;
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

    //抛物线Activity
    public void parabola(View v) {
        ActivityUtil.startActivity(getContext(), ParabolaActivity.class);
    }

    //波浪Activity
    public void wave(View v) {
        ActivityUtil.startActivity(getContext(), WaveActivity.class);
    }

    //小红点Activity
    public void dot(View v) {
        ActivityUtil.startActivity(getContext(), DotActivity.class);
    }

    // 点赞特效
    public void bubble(View v) {
        ActivityUtil.startActivity(getContext(), BubbleActivity.class);
    }


}
