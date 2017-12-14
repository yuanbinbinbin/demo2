package com.yb.demo.other.memoryleak;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by yb on 2017/12/14.
 */
public class SamsungSoftKeyBoard {
/**
 * 下面的操作是为了解决LoginActivity在Sumsung手机上由于剪切板出现内存泄漏问题
 * 4.4 1（9） - 5.0 （20）
 * http://blog.csdn.net/xingchenxiao/article/details/48549215
 */
//    try {
//        if ("samsung".equalsIgnoreCase(Build.MANUFACTURER) &&
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
//                Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//            Class cls = Class.forName("android.sec.clipboard.ClipboardUIManager");
//            Method m = cls.getDeclaredMethod("getInstance", Context.class);
//            m.setAccessible(true);
//            Object o = m.invoke(null, getApplicationContext());
//        }
//    } catch (Throwable e) {
//        e.printStackTrace();
//    }
}
