package com.base.baselibrary.jni.security;

/**
 * native加密
 * Created by yb on 2018/3/9.
 */

public class SecurityUtil {
    static {
        // 加载C代码库, 库的名称, 必须是CMakeLists.txt中指定的名称
        System.loadLibrary("base-lib");
    }

    public static native String encrypt(String str, String key);

    public static native String decrypt(String str, String key);
}
