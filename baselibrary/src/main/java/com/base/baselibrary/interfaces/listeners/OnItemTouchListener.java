package com.base.baselibrary.interfaces.listeners;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yb on 2017/8/18.
 */
public interface OnItemTouchListener {
    void onItemTouch(View v, int position, MotionEvent event, Object... objects);
}
