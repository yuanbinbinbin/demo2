package com.base.baselibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.baselibrary.BaseGlobalVariable;
import com.base.baselibrary.interfaces.listeners.OnItemClickListener;

/**
 * ListView RecyclerView 不同item 均继承此类
 * Created by yb on 2017/8/16.
 */
public abstract class WrapItemBase<T> {
    protected Activity mActivity;
    private OnItemClickListener onItemClickListener;

    public WrapItemBase() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected void onItemClick(View v, int position, Object... objects) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, position, objects);
        }
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public Context getContext() {
        if (getActivity() != null) {
            return getActivity();
        }
        return BaseGlobalVariable.getApplication();
    }

    protected View getView(int layoutid) {
        Context context = getContext();
        if (context != null) {
            return LayoutInflater.from(context).inflate(layoutid, null);
        } else {
            return null;
        }
    }

    public abstract View createView();

    public abstract void updateView(T t, int position, View convertView);
}
