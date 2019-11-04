package com.yb.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yb.demo.R;


public class LoadingDialog extends Dialog {

    public static LoadingDialog newInstance(Context context) {
        return new LoadingDialog(context);
    }

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.DialogStyle1);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading);
    }
}
