package com.base.baselibrary.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.base.baselibrary.BaseGlobalVariable;
import com.base.baselibrary.adapter.WrapItemBase;
import com.base.baselibrary.interfaces.listeners.OnItemClickListener;

/**
 * Created by yb on 2017/8/16.
 */
public class BaseViewHolder<T extends WrapItemBase> extends RecyclerView.ViewHolder {
    private T wrap;

    public BaseViewHolder(View itemView, T wrap) {
        super(itemView);
        this.wrap = wrap;
    }

    public T getWrap() {
        return wrap;
    }
}
