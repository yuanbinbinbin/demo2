package com.base.baselibrary.jni.daemon;

/**
 * Created by Administrator on 2018/3/6.
 */

public class NativeDaemonUtil {
    static {
        // 加载C代码库, 库的名称, 必须是CMakeLists.txt中指定的名称
        System.loadLibrary("base-lib");
    }

    public static native void start(String serviceName, String listenFilePath, String packagePath, String url);

}
