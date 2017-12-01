package com.yb.demo.weights.bezier.dot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;

import com.yb.demo.utils.DeviceUtil;

/**
 * Created by yb on 2017/11/30.
 */
public class DotViewHelper {
    private WindowManager mWm;
    private WindowManager.LayoutParams mParams;

    private DotView dotView;
    private View childView;
    private Context mContext;

    public DotViewHelper(Context mContext, View childView) {
        this.mContext = mContext;
        this.childView = childView;
        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSPARENT;
        childView.setOnTouchListener(onTouchListener);
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    doDown(v, event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    doMove(v, event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    doUp(v, event);
                    break;
            }
            dotView.onTouchEvent(event);
            return true;
        }
    };

    private void doUp(View v, MotionEvent event) {

    }

    private void doMove(View v, MotionEvent event) {

    }

    private void doDown(View v, MotionEvent event) {
        //申请ViewGroup不拦截
        ViewParent viewParent = v.getParent();
        if (viewParent == null) {
            return;
        }
        viewParent.requestDisallowInterceptTouchEvent(true);

        //初始化dotView
        dotView = new DotView(mContext);
        dotView.setOnAdherentListener(onAdherentListener);

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        dotView.init(v.getWidth(), v.getHeight(), location[0], location[1] - DeviceUtil.getStatusBarHeight(mContext));
        dotView.setIsResizeWhenAnimEnd(false);

        //获取View的bitmap
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        dotView.setBitmap(bitmap);


        //将DotView添加到屏幕上
        if (mWm == null) {
            mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        mWm.addView(dotView, mParams);

        //隐藏View
        v.setVisibility(View.INVISIBLE);
    }

    //View粘连状态回调
    private DotView.OnAdherentListener onAdherentListener = new DotView.OnAdherentListener() {
        @Override
        public void onDismiss() {
            removeDotView();
        }

        @Override
        public void onAnimationEnd(boolean isAdherent) {
            if (isAdherent) {
                childView.setVisibility(View.VISIBLE);
            }
            removeDotView();
        }
    };

    private void removeDotView() {
        if (dotView != null && mWm != null) {
            mWm.removeView(dotView);
        }
    }
}
