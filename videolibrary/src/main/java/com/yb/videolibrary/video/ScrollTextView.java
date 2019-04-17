package com.yb.videolibrary.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollTextView extends TextView{

    public ScrollTextView(Context context) {
        super(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
