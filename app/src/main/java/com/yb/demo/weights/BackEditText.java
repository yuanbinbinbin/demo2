package com.yb.demo.weights;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * 在软键盘弹出的情况下 监听物理返回按键
 * Created by yb on 2017/9/8.
 */
public class BackEditText extends EditText {

    public BackEditText(Context context) {
        super(context);
    }

    public BackEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && onBackClickListener != null) {
            onBackClickListener.onBack(this);
        }
        return super.onKeyPreIme(keyCode, event);
    }

    private OnBackClickListener onBackClickListener;

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public interface OnBackClickListener {
        void onBack(View v);
    }
}
