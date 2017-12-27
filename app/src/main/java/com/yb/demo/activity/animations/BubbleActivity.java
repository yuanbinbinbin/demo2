package com.yb.demo.activity.animations;

import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.weights.bezier.bubble.BubbleBean;
import com.yb.demo.weights.bezier.bubble.BubbleSurfaceView;
import com.yb.demo.weights.bezier.bubble.BubbleView;

public class BubbleActivity extends BaseActivity {

    private BubbleView mBubbleView;
    private BubbleSurfaceView mBubbleSurfaceView;
    private int[] res = new int[]{R.drawable.live_ic_donuts, R.drawable.live_ic_glasses, R.drawable.live_ic_good, R.drawable.live_ic_kele, R.drawable.live_ic_love1, R.drawable.live_ic_love2, R.drawable.live_ic_love3
            , R.drawable.live_ic_love6, R.drawable.live_ic_sandwich};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bubble;
    }

    @Override
    protected void initView() {
        mBubbleView = (BubbleView) findViewById(R.id.id_activity_bubble_bubble_view);
        mBubbleSurfaceView = (BubbleSurfaceView) findViewById(R.id.id_activity_bubble_bubble_surface_view);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    boolean isStop = false;

    @Override
    protected void initListener() {

    }

    boolean hasStart = false;

    public void start(View v) {
        if (hasStart) {
            return;
        }
        hasStart = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bubble(mBubbleView);
                            }
                        });
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    boolean hasStart2 = false;
    public void start2(View v) {
        if (hasStart2) {
            return;
        }
        hasStart2 = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bubbleSurfaceView(mBubbleSurfaceView);
                            }
                        });
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // 点赞特效
    public void bubble(View v) {
        int pic = (int) (Math.random() * 10);
        if (pic >= res.length) {
            pic = res.length - 1;
        }
        mBubbleView.addBubble(new BubbleBean(getContext(), res[pic], mBubbleView));
    }

    // 点赞特效
    public void bubbleSurfaceView(View v) {
        int pic = (int) (Math.random() * 10);
        if (pic >= res.length) {
            pic = res.length - 1;
        }
        mBubbleSurfaceView.addBubble(new BubbleBean(getContext(), res[pic], mBubbleSurfaceView));
    }
}
