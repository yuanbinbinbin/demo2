package com.yb.demo.adapter.listviews;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.base.baselibrary.adapter.listview.ListViewSingleItemBaseAdapter;
import com.base.baselibrary.adapter.listview.ListViewViewHolder;
import com.yb.demo.R;
import com.yb.demo.utils.SafeConvertUtil;

import java.util.List;

/**
 * Created by yb on 2017/2/17.
 */
public class SimpleAdapter extends ListViewSingleItemBaseAdapter<String, SimpleAdapter.ViewHolder> {

    public SimpleAdapter(Activity mContext) {
        super(mContext);
    }

    public SimpleAdapter(Activity mContext, List<String> list) {
        this(mContext);
        mList = list;
    }

    @Override
    protected ViewHolder createView() {
        View convertView = getView(R.layout.item_main);
        if (convertView == null) {
            return null;
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    protected void updateView(String item, int position, ViewHolder viewHolder) {
        if (viewHolder != null) {
            viewHolder.mTvText.setText(SafeConvertUtil.convertToString(item, ""));
        }
    }

    class ViewHolder extends ListViewViewHolder {
        TextView mTvText;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvText = (TextView) itemView.findViewById(R.id.id_item_main_tv);
        }
    }
}
