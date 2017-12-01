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
public class CommonIntro {

    public static View getView(final Context mContext, String title, String content, String left, String right, final DialogManager.OnClickListenerContent listener) {
        if (mContext == null) {
            return null;
        }
        View mView = null;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_common_intro, null);
        TextView mTitle = (TextView) mView.findViewById(R.id.id_dialog_common_intro_title);
        TextView contentTv = (TextView) mView.findViewById(R.id.id_dialog_common_intro_content);
        TextView mCancelTv = (TextView) mView.findViewById(R.id.id_dialog_common_intro_cancel);
        TextView mYesTv = (TextView) mView.findViewById(R.id.id_dialog_common_intro_ok);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            contentTv.setText(content);
        }
        if (!TextUtils.isEmpty(left)) {
            mCancelTv.setText(left);
        }
        if (!TextUtils.isEmpty(right)) {
            mYesTv.setText(right);
        }

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view, null);
                }
            }
        });
        mYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view, null);
                }
            }
        });
        return mView;
    }
}
