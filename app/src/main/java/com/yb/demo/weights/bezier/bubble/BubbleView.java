package com.yb.demo.weights.bezier.bubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by yb on 2017/12/1.
 */
public class BubbleView extends View {
    //刷新间隔
    private int REFRESH_INTERVAL = 10;
    private boolean isRunning;
    private ArrayList<BubbleBean> mBubbles;

    //更新Handler
    private Handler refreshHandler;

    //画笔
    private Paint paint;

    public BubbleView(Context context) {
        super(context);
        init();
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        if (!isRunning) {
            return;
        }
        if (mBubbles == null || mBubbles.size() <= 0) {
            isRunning = false;
            return;
        }
        Iterator<BubbleBean> beanInterator = mBubbles.iterator();
        while (beanInterator.hasNext()) {
            BubbleBean bean = beanInterator.next();
            bean.draw(canvas, paint);
            if (bean.isEnd) {
                beanInterator.remove();
            }
        }
        refreshHandler.postDelayed(runnable, REFRESH_INTERVAL);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mBubbles != null){
            for(BubbleBean bean :mBubbles){
                if(bean != null){
                    bean.stop();
                }
            }
        }
        if (refreshHandler != null) {
            isRunning = false;
            refreshHandler.removeCallbacksAndMessages(null);
        }
    }

    private void init() {
        isRunning = false;
        mBubbles = new ArrayList<BubbleBean>();
        refreshHandler = new Handler();
        paint = new Paint();
    }


    public void addBubble(BubbleBean bean) {
        mBubbles.add(bean);
        if (!isRunning) {
            isRunning = true;
            invalidate();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}
