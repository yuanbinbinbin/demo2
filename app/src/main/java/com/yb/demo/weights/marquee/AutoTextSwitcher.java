package com.yb.demo.weights.marquee;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextSwitcher;

import java.util.List;

/**
 * 自动轮训List text内容<br>
 * 建议在onStart 调用setIsShowing(true) onStop 调用setIsShowing(false)
 * Created by yb on 2017/12/8.
 */
public class AutoTextSwitcher extends TextSwitcher {

    //轮播的文字内容
    private List<String> texts;

    //停顿时间 ms
    private long delayTime = 3000;

    //是否在轮播
    private boolean isFilling;

    //目前显示的位置
    private int position = -1;

    //是否正在显示
    private boolean isShowing = false;
    //text切换的 runnable
    private Runnable nextTextRunnable = new Runnable() {
        @Override
        public void run() {
            showNextText();
        }
    };

    //是否中断 人为
    private boolean isIntercept = false;

    public void intercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    //设置停顿时间
    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public void setTexts(List<String> texts) {

        if (texts != null && texts.size() > 0) {
            this.texts = texts;
            if (!isFilling && isShowing) {
                showNextText();
            }
        }
    }

    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
        if (!isFilling && isShowing) {
            showNextText();
        }
    }

    public AutoTextSwitcher(Context context) {
        super(context);
        init();
    }

    public AutoTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setIsShowing(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //父Activity 执行onDestroy方法才会回调此方法
        isShowing = false;
    }

    @Override
    public void reset() {
        isFilling = false;
        position = -1;
        super.reset();
    }

    public void showNextText() {
        if (texts == null || texts.size() <= 0) {
            reset();
        } else {
            if (!isIntercept && isShowing) {
                position++;
                position = position % texts.size();
                if (!TextUtils.isEmpty(texts.get(position))) {
                    setText(texts.get(position));
                }
                isFilling = true;
                postDelayed(nextTextRunnable, delayTime);
            } else {
                isFilling = false;
            }
        }
    }

}
