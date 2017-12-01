package com.yb.demo.entity.eventbus;

/**
 * Created by yb on 2017/3/7.
 */
public class Item {
    private int id;
    private String msg;

    public Item() {
    }

    public Item(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
