package com.yb.demo.weights.bezier;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/11/28.
 */
public class WaveView extends View {
    public static final float WAVE_PEAK_PERCENT = 0.25f;//波峰占控件高度的百分比
    public static final float WAVE_TROUGH_PERCENT = 0.25f;//波谷占控件高度的百分比

    /**
     * 波峰
     */
    private float mWavePeak = 0f;
    /**
     * 波槽
     */
    private float mWaveTrough = 0f;
    /**
     * 水位
     */
    private float mWaveHeight = 0f;

    private Paint mPaint;

    private Path mPath;

    private int mWaterColor = 0xBB0000FF;

    private float percent = 0.0f;//完成度 1.0表示已经完成

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaterColor);

        point1 = new PointF();
        point2 = new PointF();
        point3 = new PointF();
        point4 = new PointF();
        point5 = new PointF();

        mControlPoint1 = new PointF();
        mControlPoint2 = new PointF();
        mControlPoint3 = new PointF();
        mControlPoint4 = new PointF();
    }

    private float mWidth, mHeight;

    private PointF point1, point2, point3, point4, point5;

    private PointF mControlPoint1, mControlPoint2, mControlPoint3, mControlPoint4;

    private boolean mIsRunning = false;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        Log.e("test", "onSizeChanged:" + mWidth + " " + mHeight);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsRunning || percent <= 0) {
            return;
        }
        mPath.reset();
        mPath.moveTo(point1.x, point1.y);
        mPath.quadTo(mControlPoint1.x, mControlPoint1.y, point2.x, point2.y);
        mPath.quadTo(mControlPoint2.x, mControlPoint2.y, point3.x, point3.y);
        mPath.quadTo(mControlPoint3.x, mControlPoint3.y, point4.x, point4.y);
        mPath.quadTo(mControlPoint4.x, mControlPoint4.y, point5.x, point5.y);
        mPath.lineTo(point5.x, mHeight);
        mPath.lineTo(point1.x, mHeight);
        mPath.lineTo(point1.x, point1.y);
        canvas.drawPath(mPath, mPaint);
        if (percent >= 1) {
            mIsRunning = false;
            percent = 1;
        }
    }

    private void reset(float dx) {
        mWavePeak = mWaveHeight + (mHeight - mWaveHeight) * WAVE_PEAK_PERCENT;
        mWaveTrough = mWaveHeight - (mHeight - mWaveHeight) * WAVE_PEAK_PERCENT;

        float width2 = mWidth / 2f;
        point1.x = -mWidth + dx;
        point1.y = mWaveHeight;

        point2.x = -width2 + dx;
        point2.y = mWaveHeight;

        point3.x = 0 + dx;
        point3.y = mWaveHeight;

        point4.x = width2 + dx;
        point4.y = mWaveHeight;

        point5.x = mWidth + dx;
        point5.y = mWaveHeight;

        float width4 = mWidth / 4f;
        mControlPoint1.x = point1.x + width4;
        mControlPoint1.y = mWavePeak;

        mControlPoint2.x = point2.x + width4;
        mControlPoint2.y = mWaveTrough;

        mControlPoint3.x = point3.x + width4;
        mControlPoint3.y = mWavePeak;

        mControlPoint4.x = point4.x + width4;
        mControlPoint4.y = mWaveTrough;
    }

    ValueAnimator valueAnimator;

    private void startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0, mWidth);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(Animation.INFINITE);
        //动画效果重复
//        valueAnimator.setRepeatMode(Animation.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                reset((Float) animation.getAnimatedValue());
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (percent >= 1) {
                    stop();
                }
            }
        });
        valueAnimator.start();
    }

    public void start() {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        mWaveHeight = 0;
        percent = 0;
        reset(0);
        startAnim();
    }

    public void stop() {
        if (!mIsRunning || valueAnimator == null) {
            return;
        }
        mIsRunning = false;
        valueAnimator.end();
    }

    public void updateWaveHeight(float heightPercent) {
        if (heightPercent < 0) {
            return;
        }
        if (heightPercent > 1) {
            heightPercent = 1;
        }
        Log.e("test", "percent" + heightPercent);
        percent = heightPercent;
        mWaveHeight = (mHeight) * (1 - heightPercent);
    }

    public boolean isRunning() {
        return mIsRunning;
    }
}
