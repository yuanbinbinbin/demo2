package com.yb.demo.dialog.stub;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.dialog.DialogManager;
import com.yb.demo.utils.DeviceUtil;
import com.yb.demo.weights.BackEditText;

/**
 * Created by yb on 2017/11/16.
 */
public class CommonSent {

    public static View getView(final Context mContext, String hint, String sendBtTitle, int sendBg, final DialogManager.OnClickListenerContent listener, final DialogManager dialogManager) {
        if (mContext == null) {
            return null;
        }
        View mView = null;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_common_sent, null);
        TextView mSentTv = (TextView) mView.findViewById(R.id.dialog_btn_send);
        final BackEditText mSentContentTv = (BackEditText) mView.findViewById(R.id.dialog_content_edtitext);

        if (!TextUtils.isEmpty(hint)) {
            mSentContentTv.setHint(hint);
        }
        if (!TextUtils.isEmpty(sendBtTitle)) {
            mSentTv.setText(sendBtTitle);
        }
        if (sendBg > 0) {
            mSentTv.setBackgroundResource(sendBg);
        }

        mSentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, mSentContentTv.getText().toString());
                }
            }
        });
        dialogManager.setmFocusView(mSentContentTv);
        mSentTv.post(new Runnable() {
            @Override
            public void run() {
                if (mSentContentTv != null) {
                    mSentContentTv.clearFocus();
                    mSentContentTv.requestFocus();
                    mSentContentTv.findFocus();
                    DeviceUtil.showIme(mContext, mSentContentTv);
                }
            }
        });
        mSentContentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSentContentTv != null) {
                    mSentContentTv.clearFocus();
                    mSentContentTv.requestFocus();
                    mSentContentTv.findFocus();
                    DeviceUtil.showIme(mContext, mSentContentTv);
                }
            }
        });

        mSentContentTv.setOnBackClickListener(new BackEditText.OnBackClickListener() {
            @Override
            public void onBack(View v) {
                if (dialogManager != null) {
                    dialogManager.dismissDialog();
                }
            }
        });
        return mView;
    }
}
