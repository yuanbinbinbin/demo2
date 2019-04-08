package com.base.baselibrary.net.builder;


import android.text.TextUtils;

import com.base.baselibrary.net.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    protected String url;
    private String innerUrl;
    private String innerPath;
    protected Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected int id;

    public T id(int id) {
        this.id = id;
        return (T) this;
    }

    public T url(String url) {
        this.innerUrl = url;
        if (!TextUtils.isEmpty(innerPath)) {
            this.url = innerUrl + innerPath;
        } else {
            this.url = innerUrl;
        }
        return (T) this;
    }

    public T path(String url) {
        this.innerPath = url;
        if (!TextUtils.isEmpty(innerUrl)) {
            this.url = innerUrl + innerPath;
        }
        return (T) this;
    }

    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
