package com.yb.demo.entity.dialog;

/**
 * Created by yb on 2017/11/16.
 */
public class ListItem {
    String content;
    Object item;

    public ListItem(String content, Object item) {
        this.content = content;
        this.item = item;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
