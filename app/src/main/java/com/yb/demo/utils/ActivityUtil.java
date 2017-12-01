package com.yb.demo.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by yb on 2017/2/17.
 */
public class ActivityUtil {
    public static void startActivity(Context mContext,Class clas){
        Intent intent = new Intent(mContext,clas);
        mContext.startActivity(intent);
    }
}
