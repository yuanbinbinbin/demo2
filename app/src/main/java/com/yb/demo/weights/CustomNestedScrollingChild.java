package com.yb.demo.weights;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by yb on 2017/9/18.
 */
public class CustomNestedScrollingChild extends RelativeLayout {

    public CustomNestedScrollingChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedScrollingChild(Context context) {
        super(context);
    }
}
