package com.yb.demo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by yb on 2017/2/17.
 */
public class ToastUtil {
    public static void showShortTime(Context mContext,String text){
        if(!TextUtils.isEmpty(text)){
            Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
        }
    }
    public static void showLongTime(Context mContext,String text){
        if(!TextUtils.isEmpty(text)){
            Toast.makeText(mContext,text,Toast.LENGTH_LONG).show();
        }
    }
}
