package com.base.baselibrary.jni.simple;

/**
 * Created by yb on 2018/3/6.
 */

public class Simple1 {
    static {
        // 加载C代码库, 库的名称, 必须是CMakeLists.txt中指定的名称
        System.loadLibrary("base-lib");
    }

    public static native String sayHello(String str);
}
