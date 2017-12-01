package com.yb.demo.adapter.listviews.animations;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.baselibrary.adapter.listview.ListViewSingleItemBaseAdapter;
import com.base.baselibrary.adapter.listview.ListViewViewHolder;
import com.yb.demo.R;
import com.yb.demo.weights.bezier.dot.DotViewHelper;

/**
 * Created by yb on 2017/11/29.
 */
public class DotAdapter extends ListViewSingleItemBaseAdapter<Integer, DotAdapter.ViewHolder> {

    public DotAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected ViewHolder createView() {
        View convertView = getView(R.layout.item_dot);
        if (convertView == null) {
            return null;
        }
        ViewHolder vh = new ViewHolder(convertView);
        return vh;
    }

    @Override
    protected void updateView(final Integer item, final int position, ViewHolder viewHolder) {
        if (viewHolder != null) {
            new DotViewHelper(mContext, viewHolder.mTv1);
            new DotViewHelper(mContext, viewHolder.mTv2);
            new DotViewHelper(mContext, viewHolder.mTv3);
        }
    }

    class ViewHolder extends ListViewViewHolder {
        View mTv1;
        View mTv2;
        View mTv3;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv1 = itemView.findViewById(R.id.id_item_dot_tv1);
            mTv2 = itemView.findViewById(R.id.id_item_dot_tv2);
            mTv3 = itemView.findViewById(R.id.id_item_dot_tv3);
        }
    }
}
