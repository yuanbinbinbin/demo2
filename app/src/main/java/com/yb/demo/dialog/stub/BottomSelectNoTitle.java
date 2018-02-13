package com.yb.demo.dialog.stub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yb.demo.R;
import com.yb.demo.adapter.listviews.dialog.DialogListAdapter;
import com.yb.demo.dialog.DialogManager;
import com.yb.demo.entity.dialog.ListItem;
import com.yb.demo.utils.DeviceUtil;

import java.util.List;

/**
 * Created by yb on 2017/11/16.
 */
public class BottomSelectNoTitle {

    public static View getView(Context mContext, List<ListItem> contents, final DialogManager.OnClickListenerContent listener) {
        if (mContext == null) {
            return null;
        }
        View mView = null;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_list, null);
        final View cancel = mView.findViewById(R.id.dialog_list_tv_cancel);
        final ListView listView = (ListView) mView.findViewById(R.id.dialog_list_lv);
        //ListView高度不超过屏幕的一半
        int itemHeight = DeviceUtil.dp2px(mContext, 50);
        int height = contents.size() * itemHeight;
        int screenHeight = DeviceUtil.getScreenHeight(mContext);
        if (height > screenHeight / 2) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
            params.height = screenHeight / 2 - itemHeight;
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
