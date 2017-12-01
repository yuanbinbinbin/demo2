package com.base.baselibrary.adapter.listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.base.baselibrary.interfaces.listeners.OnItemClickListener;
import com.base.baselibrary.interfaces.listeners.OnItemTouchListener;

import java.util.List;

/**
 * adapter基类
 * Created by yb on 2017/2/17.
 */
public abstract class ListViewSingleItemBaseAdapter<T, VH extends ListViewViewHolder> extends BaseAdapter {
    protected List<T> mList;//存放数据的list集合
    protected Context mContext;
    protected OnItemClickListener onItemClickListener;
    protected OnItemTouchListener onItemTouchListener;

    public ListViewSingleItemBaseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(List<T> list) {
        mList = list;
        notifyDataSetChanged();//刷新界面
    }

    /**
     * 追加数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        if (mList == null) {
            mList = list;
        } else {
            if (list != null) {
                for (T item : list) {
                    mList.add(item);
                }
            }
        }
        notifyDataSetChanged();//刷新界面
    }

    /**
     * 插入数据
     *
     * @return
     */
    public void insertDatasAtPosition(List<T> list, int position) {
        if (list == null || position > getCount()) {
            return;
        }
        if (mList == null) {
            mList = list;
        } else {
            mList.addAll(position, list);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取某条数据内容，供外界调用
     *
     * @param position
     * @return
     */
    public T getData(int position) {
        if (getCount() <= position) {
            return null;
        }
        return getItem(position);
    }

    /**
     * 根据layout id获取view
     *
     * @param id
     * @return
     */
    protected View getView(int id) {
        if (mContext == null) {
            return null;
        }
        return LayoutInflater.from(mContext).inflate(id, null);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            VH viewHolder = createView();
            convertView = viewHolder.getItemView();
        }
        if (convertView == null) {
            return null;
        }
        updateView(getItem(position), position, (VH) convertView.getTag());
        return convertView;
    }

    protected void onItemClick(View view, int position, Object... objects) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, position, objects);
        }
    }

    protected void onItemTouch(View view, int position, MotionEvent motionEvent, Object... objects) {
        if (onItemTouchListener != null) {
            onItemTouchListener.onItemTouch(view, position, motionEvent, objects);
        }
    }

    /**
     * 更新某条数据
     *
     * @param position
     * @param lv
     */
    //更新某条数据
    public void notifyItemChanged(int position, ListView lv) {
        if (position >= 0 && lv != null) {
            int firstVisiblePosition = lv.getFirstVisiblePosition();
            int lastVisiblePosition = lv.getLastVisiblePosition();
            if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
                getView(position, lv.getChildAt(position - firstVisiblePosition), lv);
            }
        }
    }

    /**
     * 创建新的view
     *
     * @return
     */
    protected abstract VH createView();

    /**
     * 更新view
     *
     * @param item
     * @param position
     * @param vh
     */
    protected abstract void updateView(T item, int position, VH vh);
}
