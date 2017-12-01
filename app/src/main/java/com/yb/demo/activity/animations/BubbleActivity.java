package com.yb.demo.activity.animations;

import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.weights.bezier.bubble.BubbleBean;
import com.yb.demo.weights.bezier.bubble.BubbleView;

public class BubbleActivity extends BaseActivity {

    private BubbleView mBubbleView;

    private int[] res = new int[]{R.drawable.live_ic_donuts, R.drawable.live_ic_glasses, R.drawable.live_ic_good, R.drawable.live_ic_kele, R.drawable.live_ic_love1, R.drawable.live_ic_love2, R.drawable.live_ic_love3
            , R.drawable.live_ic_love6, R.drawable.live_ic_sandwich};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bubble;
    }

    @Override
    protected void initView() {
        mBubbleView = (BubbleView) findViewById(R.id.id_activity_bubble_bubble_view);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    // 点赞特效
    public void bubble(View v) {
        int pic = (int) (Math.random() * 10);
        if (pic >= res.length) {
            pic = res.length - 1;
        }
        mBubbleView.addBubble(new BubbleBean(getContext(), res[pic], mBubbleView));
    }
}
