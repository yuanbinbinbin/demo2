package com.yb.demo.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/17.
 */
public class Okhttp3Test {
    //GitHub  https://github.com/square/okhttp

    public static void main(String[] args) {
//        System.out.println("66666");
        simpleGet();
//        simplePost();
    }

    private static void simpleGet() {
        OkHttpClient client = new OkHttpClient();
        System.out.println("thread: " + Thread.currentThread().getName());//thread: main
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
//                response.isSuccessful()
//                response.isRedirect()  //是否重定向
                String body = response.body().string();
                System.out.println("thread: " + Thread.currentThread().getName());//thread: OkHttp http://www.baidu.com/..
                System.out.println(code + " body: " + body);
            }
        });

        //当前线程同步请求
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            String body = response.body().string();
            System.out.println("thread: " + Thread.currentThread().getName());//thread: OkHttp thread: main
            System.out.println(code + " body: " + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void simplePost() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "我是请求body");
        Request request = new Request.Builder().url("http://www.baidu.com").post(body).build();
        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(""+response.body().string());
            }
        });
        //当前线程同步请求
        try {
            Response response = client.newCall(request).execute();
            System.out.println(""+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //HTTP 协议介绍

}
