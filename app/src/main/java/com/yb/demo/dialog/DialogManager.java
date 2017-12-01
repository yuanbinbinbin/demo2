package com.yb.demo.dialog;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.demo.R;
import com.yb.demo.dialog.stub.BottomSelectNoTitle;
import com.yb.demo.dialog.stub.BottomSelectWidthTitle;
import com.yb.demo.dialog.stub.CommonIntro;
import com.yb.demo.dialog.stub.CommonLoading;
import com.yb.demo.dialog.stub.CommonSent;
import com.yb.demo.entity.dialog.ListItem;
import com.yb.demo.utils.DeviceUtil;

import java.util.List;


/**
 * 管理Dialog的类
 *
 * @author sll
 */
public class DialogManager {
    private LayoutInflater mInflater;
    private Context mContext;
    private SimpleDialog mBaseDialog;
    private DialogStyle mStyle;

    public DialogManager(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 显示Dialog
     *
     * @param style
     * @param listener
     * @param objects
     * @author sll
     */
    public void showDialog(DialogStyle style, OnClickListenerContent listener, Object... objects) {
        mStyle = style;
        mBaseDialog = getDialog();
        switch (style) {
            case SHARE:
                break;
            case LOADING:
                operateLoading(listener, objects);
                break;
            case COMMON_INTOR:
                operateCommonIntro(listener, objects);
                break;
            case BOTTOM_SELECT_NO_TITLE:
                operateBottomSelectNoTitle(listener, objects);
                break;
            case BOTTOM_SELECT_WIDTH_TITLE:
                operateBottomSelectWidthTitle(listener, objects);
                break;
            case SEND_MSG:
                operateCommonSent(listener, objects);
                break;
            default:
                break;
        }
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            try {
                mBaseDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * List<ListItem> contents
     *
     * @param listener
     * @param objects
     */
    private void operateBottomSelectNoTitle(final OnClickListenerContent listener, Object[] objects) {
        if (objects == null || objects.length < 1) {
            return;
        }
        List<ListItem> contents = null;
        if (objects[0] != null || objects[0] instanceof List) {
            try {
                contents = (List<ListItem>) objects[0];
            } catch (Exception e) {
                contents = null;
            }
        }
        if (contents == null || contents.size() <= 0) {
            return;
        }
        View mView = BottomSelectNoTitle.getView(mContext, contents, listener);
        if (mView != null) {
            mBaseDialog.setContentView(mView);
        }
    }

    /**
     * String titleContent,List<ListItem> contents
     *
     * @param listener
     * @param objects
     */
    private void operateBottomSelectWidthTitle(final OnClickListenerContent listener, Object[] objects) {
        if (objects == null || objects.length < 2) {
            return;
        }
        String title = null;
        if (objects[0] != null && objects[0] instanceof String) {
            title = (String) objects[0];
        }

        List<ListItem> contents = null;
        if (objects[1] != null && objects[1] instanceof List) {
            try {
                contents = (List<ListItem>) objects[1];
            } catch (Exception e) {
                contents = null;
            }
        }
        if (contents == null || contents.size() <= 0) {
            return;
        }
        View mView = BottomSelectWidthTitle.getView(mContext, contents, title, listener);
        if (mView != null) {
            mBaseDialog.setContentView(mView);
        }
    }

    /**
     * String hint, String sendBtTitle, int sendBg,
     *
     * @param listener
     * @param objects
     */
    private void operateCommonSent(final OnClickListenerContent listener, final Object[] objects) {
        if (objects == null || objects.length < 3) {
            return;
        }
        String hint = "";
        String sendBtTitle = "";
        int sendBg = -1;
        if (objects[0] != null && objects[0] instanceof String) {
            hint = (String) objects[0];
        }
        if (objects[1] != null && objects[1] instanceof String) {
            sendBtTitle = (String) objects[1];
        }
        if (objects[2] != null && objects[2] instanceof Integer) {
            sendBg = (int) objects[2];
        }
        View mView = CommonSent.getView(mContext, hint, sendBtTitle, sendBg, listener, this);
        if (mView != null) {
            mBaseDialog.setContentView(mView);
        }
    }

    /**
     * String title, String content, String left, String right,
     *
     * @param listener
     * @param objects
     */
    private void operateCommonIntro(final OnClickListenerContent listener, Object[] objects) {
        if (objects == null || objects.length < 4) {
            return;
        }
        String title = "";
        String content = "";
        String left = "";
        String right = "";
        if (objects[0] != null && objects[0] instanceof String) {
            title = (String) objects[0];
        }
        if (objects[1] != null && objects[1] instanceof String) {
            content = (String) objects[1];
        }
        if (objects[2] != null && objects[2] instanceof String) {
            left = (String) objects[2];
        }
        if (objects[3] != null && objects[3] instanceof String) {
            right = (String) objects[3];
        }
        View mView = CommonIntro.getView(mContext, title, content, left, right, listener);
        if (mView != null) {
            mBaseDialog.setContentView(mView);
        }
    }

    /**
     * String content,
     *
     * @param listener
     * @param objects
     */
    private void operateLoading(OnClickListenerContent listener, Object[] objects) {
        if (objects == null || objects.length < 1) {
            return;
        }
        String content = "";
        if (objects[0] != null && objects[0] instanceof String) {
            content = (String) objects[0];
        }
        View mView = CommonLoading.getView(mContext, content, listener);
        if (mView != null) {
            mBaseDialog.setContentView(mView);
        }
    }

    /**
     * 获取Dialog实例
     *
     * @return
     */
    public SimpleDialog getDialog() {
        dismissDialog();
        mBaseDialog = null;
        //根据需求修改dialog的样式
        if (mStyle == DialogStyle.SHARE || mStyle == DialogStyle.BOTTOM_SELECT_NO_TITLE || mStyle == DialogStyle.BOTTOM_SELECT_WIDTH_TITLE) {
            mBaseDialog = new SimpleDialog(mContext, R.style.DialogStyle1);
            mBaseDialog.setCanceledOnTouchOutside(true);
            int width = DeviceUtil.getScreenWidth(mContext);
            int height = -1;
            changeDialogStyle(width, height, Gravity.BOTTOM, R.style.in_down_out_down);
        } else if (mStyle == DialogStyle.SEND_MSG) {
            mBaseDialog = new SimpleDialog(mContext, R.style.DialogStyle1);
            mBaseDialog.setCanceledOnTouchOutside(true);
            int width = DeviceUtil.getScreenWidth(mContext);
            int height = -1;
            changeDialogStyle(width, height, Gravity.BOTTOM, -1);
            mBaseDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismissDialog();
                    }
                    return false;
                }
            });
        } else {
            mBaseDialog = new SimpleDialog(mContext, R.style.DialogStyle1);
            mBaseDialog.setCanceledOnTouchOutside(true);
        }

        return mBaseDialog;
    }

    private void changeDialogStyle(int width, int height, int gravity, int anim) {
        if (mBaseDialog == null) {
            return;
        }
        Window window = mBaseDialog.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        if (width > 0) {
            params.width = width;
        }
        if (height > 0) {
            params.height = height;
        }
        window.setGravity(gravity);
        window.setAttributes(params);
        if (anim > 0) {
            window.setWindowAnimations(anim);
        }
    }

    public void dismissDialog() {
        if (mBaseDialog != null && mBaseDialog.isShowing()) {
            try {
                mBaseDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShowDialog() {
        if (mBaseDialog != null) {
            return mBaseDialog.isShowing();
        } else {
            return false;
        }
    }

    public interface OnClickListenerContent {
        void onClick(View view, Object... content);
    }

    public void setmFocusView(View mFocusView) {
        if (mBaseDialog != null) {
            mBaseDialog.setmFocusView(mFocusView);
        }
    }


    public enum DialogStyle {
        SHARE,
        LOADING,
        COMMON_INTOR,
        SEND_MSG,
        BOTTOM_SELECT_NO_TITLE,
        BOTTOM_SELECT_WIDTH_TITLE
    }
}
