package com.base.baselibrary.adapter.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter Base
 * 适用于item单一的RecyclerView
 * Created by yb on 2017/8/18.
 */
public abstract class RecyclerViewSingleItemBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mList;
    protected Activity mContext;

    public RecyclerViewSingleItemBaseAdapter(Activity mContext) {
        super();
        this.mContext = mContext;
    }

    /**
     * 设置数据
     *
     * @param list
     */
    //设置数据
    public void setData(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    /**
     * 末尾添加数据
     *
     * @param list
     */
    //添加数据
    public void addData(List<T> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        if (getItemCount() <= 0) {
            mList = list;
        } else {
            for (T t : list) {
                mList.add(t);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 末尾添加一条数据
     *
     * @param item
     */
    //添加数据
    public void addData(T item) {
        if (item == null) {
            return;
        }
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.add(item);
        notifyDataSetChanged();
    }

    protected T getItem(int position) {
        if (position >= getItemCount()) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
