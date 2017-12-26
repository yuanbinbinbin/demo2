package com.yb.demo.weights.marquee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewSwitcher;

import java.util.List;

/**
 * 轮播View<br>
 * 有且仅有一条数据时，不会轮播<br>
 * android:inAnimation="@anim/anim_down_in"<br>
 * android:outAnimation="@anim/anim_up_out"
 * Created by yb on 2017/12/8.
 */
public class MarqueeView extends ViewSwitcher {

    //帮助生成view和更新view
    private MarqueeViewBaseAdapter baseAdapter;

    //停顿时间 ms
    private long delayTime = 3000;

    //是否在轮播
    private boolean isFilling;

    //目前显示的位置
    private int position = -1;

    //是否正在显示
    private boolean isShowing = false;
    //是否中断 人为
    private boolean isIntercept = false;

    //text切换的 runnable
    private Runnable nextTextRunnable = new Runnable() {
        @Override
        public void run() {
            showNextData();
        }
    };

    public MarqueeView(Context context) {
        super(context);
        init();
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    //人为中断
    public void intercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    //设置停顿时间
    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    //设置Helper
    public void setAdapter(MarqueeViewBaseAdapter he) {
        if (he == null) {
            return;
        }
        clear();
        this.baseAdapter = he;
        setFactory();
        if (!isFilling && isShowing) {
            showNextData();
        }
    }

    private void clear() {
        removeCallbacks(nextTextRunnable);
        isFilling = false;
        removeAllViews();
    }

    //View是否正在显示  建议在onStart 调用setIsShowing(true) onStop 调用setIsShowing(false)
    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
        if (isShowing && !isFilling) {
            showNextData();
        }
    }


    //更显当前view
    public void setCurrentData(Object t) {
        if (t == null || baseAdapter == null) {
            return;
        }
        View view = getCurrentView();
        if (view == null) {
            return;
        }
        baseAdapter.updateView(view, t);
    }

    //显示下一个view
    public void showNextData() {
        if (baseAdapter == null) {
            return;
        }
        List mList = baseAdapter.getmList();
        if (mList == null || mList.size() <= 0) {
            reset();
        } else {
            if (!isIntercept && isShowing) {
                position++;
                position = position % mList.size();
                Object t = mList.get(position);
                if (t == null) {
                    return;
                }
                View view = getNextView();
                if (view == null) {
                    return;
                }
                baseAdapter.updateView(view, t);
                showNext();
                if (mList.size() > 1) {
                    isFilling = true;
                    postDelayed(nextTextRunnable, delayTime);
                } else {
                    isFilling = false;
                }
            } else {
                isFilling = false;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isShowing = true;
        setIsShowing(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //父Activity 执行onDestroy方法才会回调此方法
        isShowing = false;
    }

    /**
     * 防止误调
     *
     * @param factory
     */
    @Override
    public void setFactory(ViewFactory factory) {
    }

    private void setFactory() {
        if (baseAdapter == null) {
            return;
        }
        super.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                return baseAdapter.createView(getContext());
            }
        });
    }

    public static abstract class MarqueeViewBaseAdapter<T> {
        //将要显示的数据
        private List<T> mList;

        //设置数据内容
        public void setmList(List<T> mList) {
            if (mList != null && mList.size() > 0) {
                this.mList = mList;
            }
        }

        public List<T> getmList() {
            return mList;
        }

        public abstract View createView(Context context);

        public abstract void updateView(View view, T t);
    }
}
