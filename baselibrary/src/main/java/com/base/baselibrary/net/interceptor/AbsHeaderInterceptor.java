package com.base.baselibrary.net.interceptor;


import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract class AbsHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        Map<String, String> header = headers();
        if (header != null && !header.isEmpty()) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }
        return chain.proceed(requestBuilder.build());
    }

    protected abstract Map<String, String> headers();
}
