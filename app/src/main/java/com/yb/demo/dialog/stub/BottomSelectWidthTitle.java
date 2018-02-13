package com.yb.demo.dialog.stub;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.adapter.listviews.dialog.DialogListAdapter;
import com.yb.demo.dialog.DialogManager;
import com.yb.demo.entity.dialog.ListItem;
import com.yb.demo.utils.DeviceUtil;

import java.util.List;

/**
 * Created by yb on 2017/11/16.
 */
public class BottomSelectWidthTitle {

    public static View getView(Context mContext, List<ListItem> contents, String content, final DialogManager.OnClickListenerContent listener) {
        if (mContext == null) {
            return null;
        }
        View mView = null;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_width_title, null);
        TextView mTvTitle = (TextView) mView.findViewById(R.id.dialog_list_width_title_title);
        final View cancel = mView.findViewById(R.id.dialog_list_width_title_cancle);
        final ListView listView = (ListView) mView.findViewById(R.id.dialog_list_width_title_lv);
        if (!TextUtils.isEmpty(content)) {
            mTvTitle.setText(content);
        }
        //ListView高度不超过屏幕的一半
        int itemHeight = DeviceUtil.dp2px(mContext, 50);
        int height = (contents.size() + 1) * itemHeight;
        int screenHeight = DeviceUtil.getScreenHeight(mContext);
        if (height > screenHeight / 2) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
            params.height = screenHeight / 2 - 2 * itemHeight;
            listView.setLayoutParams(params);
        }
        DialogListAdapter adapter = new DialogListAdapter(mContext, contents, -1);
        listView.setAdapter(adapter);
        adapter.setOnReportClickListener(new DialogListAdapter.OnDialogListItemClickListener() {
            @Override
            public void onDialogListItemClick(int position, Object obj) {
                if (listener != null) {
                    listener.onClick(listView, position, obj);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return mView;
    }
}
