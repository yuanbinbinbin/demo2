package com.yb.demo.weights.bezier.bubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * SurfaceView版
 * Created by yb on 2017/12/1.
 */
public class BubbleSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    //更新Handler
    private BubbleHandler refreshHandler;

    //用于更新UI
    private HandlerThread mThread;

    private SurfaceHolder mHolder;

    public BubbleSurfaceView(Context context) {
        super(context);
        init();
    }

    public BubbleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BubbleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        //背景透明
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new HandlerThread("DrawBubble");
        mThread.start();
        refreshHandler = new BubbleHandler(mThread.getLooper(), mHolder, new Paint());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (refreshHandler != null) {
            Message message = Message.obtain();
            message.what = BubbleHandler.OPERATE_DESTROY;
            refreshHandler.sendMessage(message);
        }
    }

    public void addBubble(BubbleBean bean) {
        if (bean != null && refreshHandler != null) {
            Message message = Message.obtain();
            message.what = BubbleHandler.OPERATE_ADD;
            message.obj = bean;
            refreshHandler.sendMessage(message);
        }
    }

    static class BubbleHandler extends Handler {
        //刷新间隔
        private int REFRESH_INTERVAL = 10;
        public static final int OPERATE_ADD = 0;
        public static final int OPERATE_UPDATE = 1;
        public static final int OPERATE_DESTROY = 2;
        private boolean isRunning;
        private ArrayList<BubbleBean> mBubbles;
        //画笔
        private Paint paint;

        private SurfaceHolder mHolder;

        public BubbleHandler(Looper looper, SurfaceHolder mHolder, Paint paint) {
            super(looper);
            this.mHolder = mHolder;
            this.paint = paint;
            isRunning = false;
            mBubbles = new ArrayList<BubbleBean>();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPERATE_ADD:
                    addBubble((BubbleBean) msg.obj);
                    break;
                case OPERATE_UPDATE:
                    update();
                    break;
                case OPERATE_DESTROY:
                    clear();
                    break;
            }
        }

        private void addBubble(BubbleBean bean) {
            mBubbles.add(bean);
            if (!isRunning) {
                isRunning = true;
                update();
            }
        }

        private void update() {
            if(mHolder==null){
                return;
            }
            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
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
                    Message message = Message.obtain();
                    message.what = OPERATE_UPDATE;
                    sendMessageDelayed(message, REFRESH_INTERVAL);
                } catch (Exception e) {

                } finally {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void clear() {
            removeCallbacksAndMessages(null);
            if (mBubbles != null) {
                for (BubbleBean bean : mBubbles) {
                    if (bean != null) {
                        bean.stop();
                    }
                }
            }
            isRunning = false;
            mBubbles.clear();
            mHolder = null;
            paint = null;
        }
    }

}
