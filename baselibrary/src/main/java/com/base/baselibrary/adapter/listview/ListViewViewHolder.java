package com.base.baselibrary.adapter.listview;

import android.view.View;

/**
 * Created by yb on 2017/11/29.
 */
public class ListViewViewHolder {
    protected View itemView;

    public ListViewViewHolder(View itemView) {
        this.itemView = itemView;
        if (this.itemView != null) {
            this.itemView.setTag(this);
        }
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }
}
