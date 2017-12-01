package com.yb.demo.weights.bezier;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/28.
 */
public class ParabolaView extends View {

    private int drawWidth;//面板的宽度
    private int drawHeight;//面板的高度

    private Point mStart;
    private Point mEnd;
    private Point mControl;

    public ParabolaView(Context context) {
        super(context);
    }

    public ParabolaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParabolaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawWidth = w - getPaddingLeft() - getPaddingRight();
        drawHeight = h - getPaddingTop() - getPaddingBottom();
        if (drawWidth < 0) {
            drawWidth = 0;
        }
        if (drawHeight < 0) {
            drawHeight = 0;
        }
        reset();
    }

    private void reset() {

    }
}
