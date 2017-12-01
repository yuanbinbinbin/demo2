package com.yb.demo.entity.eventbus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yb on 2017/3/7.
 */
public class ItemList {
    private List<Item> items;

    public ItemList(int count) {
        items = new ArrayList<Item>();
        for (int i = 0; i < count; i++) {
            items.add(new Item(i, "Item" + i));
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
