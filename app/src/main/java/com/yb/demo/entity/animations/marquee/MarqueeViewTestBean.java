package com.yb.demo.entity.animations.marquee;

/**
 * Created by yb on 2017/12/8.
 */
public class MarqueeViewTestBean {
    int imgRes;
    String text;

    public MarqueeViewTestBean(int imgRes, String text) {
        this.imgRes = imgRes;
        this.text = text;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
