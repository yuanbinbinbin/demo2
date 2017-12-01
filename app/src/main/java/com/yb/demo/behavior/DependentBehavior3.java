package com.yb.demo.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yb.demo.utils.DeviceUtil;

/**
 * Created by yb on 2017/9/8.
 */
public class DependentBehavior3 extends CoordinatorLayout.Behavior<View> {
    public DependentBehavior3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY(dependency.getBottom());
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        Log.e("test", "onLayoutChild dy:");
        child.setTranslationY(DeviceUtil.dp2px(child.getContext(), 200));
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
//        Log.e("test","onNested dy:"+dy+" "+consumed[1]);
        if (child.getTranslationY() > 0) {
            return;
        }
        if (dy < 0) {

        } else if (dy > 0) {
            float y = child.getTranslationY() - dy;
            if (y > 0) {
                child.setTranslationY(y);
                consumed[1] += y;
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Log.e("test","dyConsumed: "+dyConsumed+"dyUnConsumed: "+dyUnconsumed);
        if (dyConsumed > 0) {
            return;
        }
        float y = child.getTranslationY() - dyConsumed;
        if (y > 0 && y < DeviceUtil.dp2px(child.getContext(), 200)) {
            child.setTranslationY(y);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
//        return super.onInterceptTouchEvent(parent, child, ev);
        Log.e("test", "onInterceptTouchEvent" + child.getTranslationY() + "  " + child.getY());
        if (child.getTranslationY() > DeviceUtil.dp2px(child.getContext(), 55) || child.getTranslationY() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
//        return super.onTouchEvent(parent, child, ev);

        return true;
    }
}
