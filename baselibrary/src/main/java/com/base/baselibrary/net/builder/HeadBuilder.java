package com.base.baselibrary.net.builder;


import com.base.baselibrary.net.OkHttpUtils;
import com.base.baselibrary.net.request.OtherRequest;
import com.base.baselibrary.net.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
