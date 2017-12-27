package com.yb.demo.activity.exchange.meituan1;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MeiTuanMainActivity1 extends BaseActivity {

    private List<View> mItems;
    private List<View> mContainers;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mei_tuan_main1;
    }

    @Override
    protected void initView() {
        mItems = new ArrayList<View>();
        mItems.add(findViewById(R.id.id_activity_mei_tuan_main1_v1));
        mItems.get(0).setTag(0);
        mItems.add(findViewById(R.id.id_activity_mei_tuan_main1_v2));
        mItems.get(1).setTag(1);
        mItems.add(findViewById(R.id.id_activity_mei_tuan_main1_v3));
        mItems.get(2).setTag(2);
        mItems.add(findViewById(R.id.id_activity_mei_tuan_main1_v4));
        mItems.get(3).setTag(3);
        mItems.add(findViewById(R.id.id_activity_mei_tuan_main1_v5));
        mItems.get(4).setTag(4);

        mContainers = new ArrayList<View>();
        mContainers.add(findViewById(R.id.id_activity_mei_tuan_main1_container1));
        mContainers.add(findViewById(R.id.id_activity_mei_tuan_main1_container2));
        mContainers.add(findViewById(R.id.id_activity_mei_tuan_main1_container3));
        mContainers.add(findViewById(R.id.id_activity_mei_tuan_main1_container4));
        mContainers.add(findViewById(R.id.id_activity_mei_tuan_main1_container5));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBackPressed() {
        if (showPosition >= 0) {
            hide();
        } else {
            super.onBackPressed();
        }
    }

    public void onclick1(View v) {
        show(0);
    }

    public void onclick2(View v) {
        show(1);
    }

    public void onclick3(View v) {
        show(2);
    }

    public void onclick4(View v) {
        show(3);
    }

    public void onclick5(View v) {
        show(4);
    }

    //哪个标签在显示
    private int showPosition = -1;
    private float scalePosition;
    private float scaleStart;
    private int animationDuration = 500;

    private void show(int position) {
        showPosition = position;

        //初始化开始 伸展的位置 和 初始高度
        final View clickView = mItems.get(position);
        int[] location = new int[2];
        clickView.getLocationOnScreen(location);
        final View container = mContainers.get(position);
        int[] location2 = new int[2];
        container.getLocationOnScreen(location2);
        //初始高度
        scaleStart = 1.0f * clickView.getHeight() / container.getHeight();
        //初始伸缩位置
        scalePosition = 1.0f * (location[1] - location2[1] + clickView.getHeight() / 2) / container.getHeight();

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, scaleStart, 1, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, scalePosition);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clickView.setEnabled(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        container.setVisibility(View.VISIBLE);
        container.startAnimation(scaleAnimation);


        //向上平移
        for (int i = 0; i < position; i++) {
            View item = mItems.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(item, "translationY", item.getTranslationY(), -(item.getHeight() + item.getTop()));
            animator.setDuration(animationDuration).start();
        }

        //获取父View的高度
        int parentHeight = ((ViewGroup) clickView.getParent()).getHeight();

        //向下平移
        for (int i = position + 1; i < mItems.size(); i++) {
            View item = mItems.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(item, "translationY", item.getTranslationY(), (parentHeight - item.getTop()));
            animator.setDuration(animationDuration).start();
        }
    }

    private void hide() {
        final View clickView = mItems.get(showPosition);
        final View container = mContainers.get(showPosition);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 1, scaleStart, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, scalePosition);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                clickView.setEnabled(true);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                container.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        container.startAnimation(scaleAnimation);

        //向上平移的恢复动画
        for (int i = 0; i < showPosition; i++) {
            View item = mItems.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(item, "translationY", item.getTranslationY(), 0);
            animator.setDuration(animationDuration).start();
        }
        //向下平移的恢复动画
        for (int i = showPosition + 1; i < mItems.size(); i++) {
            View item = mItems.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(item, "translationY", item.getTranslationY(), 0);
            animator.setDuration(animationDuration).start();
        }

        showPosition = -1;
    }
}
