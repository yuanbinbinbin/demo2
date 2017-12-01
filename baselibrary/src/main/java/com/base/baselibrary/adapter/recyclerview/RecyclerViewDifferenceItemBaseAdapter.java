package com.base.baselibrary.adapter.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.base.baselibrary.adapter.WrapItemBase;
import com.base.baselibrary.entity.BaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要适用于含有不同item的Recyclerview
 * Created by yb on 2017/3/21.
 */
public class RecyclerViewDifferenceItemBaseAdapter<T extends BaseItem> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mList;
    protected SparseArray<WrapItemBase<T>> mItemViews = new SparseArray<WrapItemBase<T>>();
    protected Activity mContext;

    public RecyclerViewDifferenceItemBaseAdapter(Activity mContext) {
        super();
        this.mContext = mContext;
    }

    public void addItemView(int type, WrapItemBase<T> itemView) {
        if (itemView == null) {
            return;
        }
        itemView.setActivity(mContext);
        mItemViews.put(type, itemView);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WrapItemBase<T> itemView = null;
        try {
            itemView = mItemViews.get(viewType);
        } catch (Exception e) {
            return null;
        }
        if (itemView == null) {
            return null;
        }
        return new BaseViewHolder(itemView.createView(), itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder != null && holder.getWrap() != null) {
            holder.getWrap().updateView(getItem(position), position, holder.itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        T item = getItem(position);
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        return item.getItemType();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    //获取Position位置的数据
    public T getItem(int position) {
        if (getItemCount() <= position) {
            return null;
        }
        return mList.get(position);
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

}
