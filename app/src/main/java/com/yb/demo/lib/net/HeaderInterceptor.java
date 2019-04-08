package com.yb.demo.lib.net;

import com.base.baselibrary.net.interceptor.AbsHeaderInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/4/8 16:16
 */
public class HeaderInterceptor extends AbsHeaderInterceptor {

    HashMap<String, String> headers = new HashMap<>();

    public HeaderInterceptor() {
        headers.put("key1", "a");
        headers.put("key2", "b");
        headers.put("key3", "c");
    }

    @Override
    protected Map<String, String> headers() {
        return headers;
    }
}
