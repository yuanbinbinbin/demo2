package com.yb.demo.activity.net;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.base.baselibrary.net.OkHttpUtils;
import com.base.baselibrary.net.callback.Callback;
import com.base.baselibrary.net.callback.StringCallback;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/4/8 14:22
 */
public class NetActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, NetActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void get(View view) {
        OkHttpUtils.get()
                .addParams("key1", "a")
                .addParams("key2", "b")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Log.e("test", id + "error response: ");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("test", id + "success response: " + response);
                    }

                });
    }

    public void post(View view) {
        OkHttpUtils.post()
                .addParams("post1", "哈哈")
                .addParams("post2", "b")
                .addFile("shumei", "shumei.txt", Environment.getExternalStoragePublicDirectory("/shumei.txt"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Log.e("test post", id + "error success response: ");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("test post", id + "success response: " + response);
                    }

                });
    }

    public void postBody(View view) {
        OkHttpUtils.postString()
                .addParams("postBody1", "a")
                .addParams("postBody2", "{'postbody':'a'}")
                .content("{'postbody':'a'}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Log.e("test post", id + "error success response: ");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("test post", id + "success response: " + response);
                    }

                });
    }

}
