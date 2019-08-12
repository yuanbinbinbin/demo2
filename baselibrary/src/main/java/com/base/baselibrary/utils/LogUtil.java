package com.base.baselibrary.utils;

import android.util.Log;

/**
 * Created by yb on 2017/2/16.
 */
public class LogUtil {
    private static  boolean isPullServer = false;//设置是否开启推送到服务器端日志
    private static  boolean isPrintLog = true;//是否可以打印log

    public static void i(String tag,String message){
        if (isPullServer){
           // PushLogManager.getManager().push_I(tag, message);
        }
        if (isPrintLog){
            Log.i(tag, message);
        }
    }
    public static void w(String tag,String message){
        if (isPullServer){
           // PushLogManager.getManager().push_W(tag, message);
        }
        if (isPrintLog){
            Log.w(tag, message);
        }
    }
    public static void e(String tag,String message){
        if (isPullServer){
            //PushLogManager.getManager().push_E(tag, message);
        }
        if (isPrintLog){
            Log.e(tag, message);
        }
    }
    public static void v(String tag,String message){
        if (isPullServer){
           // PushLogManager.getManager().push_V(tag, message);
        }
        if (isPrintLog){
            Log.v(tag, message);
        }
    }

    /**
     * 上传日志到服务器
     */
    public static void uploadLog(){
       // PushLogManager.getManager().uploadFile();
    }

    /**
     * 强制写，针对有崩溃的特殊情况开启，影响效率，一般不用
     * @param isForceWrite
     */
    public static void setForceWriteLog(boolean isForceWrite){
        //PushLogManager.getManager().isForeceLog(isForceWrite);
    }

    /**
     * 是否推送log到服务器
     * @param isPullServer
     */
    public static void setIsPullServer(boolean isPullServer){
      //  PushLogUtils.isPullServer = isPullServer;
    }

    /**
     * 是否打印log
     * @param isPrintLog
     */
    public static void setIsPrintLog(boolean isPrintLog){
        //PushLogUtils.isPrintLog = isPrintLog;
    }

}
