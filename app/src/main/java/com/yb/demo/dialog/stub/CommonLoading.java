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
public class CommonLoading {

    public static View getView(final Context mContext, String content, final DialogManager.OnClickListenerContent listener) {
        if (mContext == null) {
            return null;
        }
        View mView = null;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_common_loading, null);
        TextView mTvContent = (TextView) mView.findViewById(R.id.id_dialog_common_loading_content);
        if (!TextUtils.isEmpty(content)) {
            mTvContent.setText(content);
        }
        return mView;
    }
}
