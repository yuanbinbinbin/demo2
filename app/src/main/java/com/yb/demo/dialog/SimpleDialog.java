package com.yb.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.utils.DeviceUtil;

/**
 * Created by yb on 2017/11/16.
 */
public class SimpleDialog extends Dialog {
    public SimpleDialog(Context context) {
        this(context, R.style.DialogStyle1);
    }

    public SimpleDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SimpleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void dismiss() {
        if (mFocusView != null) {
            DeviceUtil.hideIme(getContext(), mFocusView);
        }
        super.dismiss();
    }

    private View mFocusView = null;

    public void setmFocusView(View mFocusView) {
        this.mFocusView = mFocusView;
    }
}
