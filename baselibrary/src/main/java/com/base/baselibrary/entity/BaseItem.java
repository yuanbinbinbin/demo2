package com.base.baselibrary.entity;

/**
 * ListView、RecyclerView 中实体基类
 * Created by yb on 2017/8/16.
 */
public class BaseItem {
    //表示Item的类型
    private int itemType;

    public BaseItem(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
