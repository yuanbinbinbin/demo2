package com.base.baselibrary.net;


import com.base.baselibrary.net.builder.GetBuilder;
import com.base.baselibrary.net.builder.HeadBuilder;
import com.base.baselibrary.net.builder.OtherRequestBuilder;
import com.base.baselibrary.net.builder.PostFileBuilder;
import com.base.baselibrary.net.builder.PostFormBuilder;
import com.base.baselibrary.net.builder.PostStringBuilder;
import com.base.baselibrary.net.callback.Callback;
import com.base.baselibrary.net.callback.HttpException;
import com.base.baselibrary.net.request.RequestCall;
import com.base.baselibrary.net.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private static String host;

    private OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public OkHttpUtils defaultHost(String url) {
        host = url;
        return this;
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        GetBuilder get = new GetBuilder();
        get.url(host);
        return get;
    }

    public static PostFormBuilder post() {
        PostFormBuilder postFormBuilder = new PostFormBuilder();
        postFormBuilder.url(host);
        return postFormBuilder;
    }

    public static PostStringBuilder postString() {
        PostStringBuilder post = new PostStringBuilder();
        post.url(host);
        return post;
    }

    public static PostFileBuilder postFile() {
        PostFileBuilder postFileBuilder = new PostFileBuilder();
        postFileBuilder.url(host);
        return postFileBuilder;
    }

    public static OtherRequestBuilder put() {
        OtherRequestBuilder otherRequestBuilder = new OtherRequestBuilder(METHOD.PUT);
        otherRequestBuilder.url(host);
        return otherRequestBuilder;
    }

    public static HeadBuilder head() {
        HeadBuilder headBuilder = new HeadBuilder();
        headBuilder.url(host);
        return headBuilder;
    }

    public static OtherRequestBuilder delete() {
        OtherRequestBuilder delete = new OtherRequestBuilder(METHOD.DELETE);
        delete.url(host);
        return delete;
    }

    public static OtherRequestBuilder patch() {
        OtherRequestBuilder patch = new OtherRequestBuilder(METHOD.PATCH);
        patch.url(host);
        return patch;
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateResponse(response, id)) {
                        sendFailResultCallback(call, new HttpException(response), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }

    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

