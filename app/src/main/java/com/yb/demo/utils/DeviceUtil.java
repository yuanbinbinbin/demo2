package com.yb.demo.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by yb on 2017/3/2.
 */
public class DeviceUtil {

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();
        return dm == null ? 0 : dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();
        return dm == null ? 0 : dm.heightPixels;
    }

    /**
     * 获取系统状态栏高度；
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取手机名称
     *
     * @return
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModelName() {
        return Build.MODEL;
    }

    /**
     * 获取IMEI识别码
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String imei = "";
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        // check if has the permission
        if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
                .checkPermission(Manifest.permission.READ_PHONE_STATE,
                        context.getPackageName())) {
            imei = manager.getDeviceId();
        }
        return TextUtils.isEmpty(imei) ? "" : imei;
    }

    /**
     * 获取App版本名<br>
     * 例：1.0.1
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return TextUtils.isEmpty(version) ? "" : version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取App版本名,带V<br>
     * 例：V1.0.1
     *
     * @param context
     * @return
     */
    public static String getVersionNameV(Context context) {
        return "V " + getVersionName(context);
    }

    /**
     * 获取App版本号<br>
     * 例：101
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取手机系统的版本号<br>
     * 例：19
     *
     * @return
     */
    public static int getSysVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机系统名<br>
     * 例：4.4
     *
     * @return
     */
    public static String getSysVersionName() {
        return Build.VERSION.RELEASE;
    }
    //    API等级1：Android 1.0
    //    API等级2：Android 1.1 Petit Four
    //    API等级3：Android 1.5 Cupcake 纸杯蛋糕
    //    API等级4：Android 1.6 Donut  甜甜圈
    //    API等级5：Android 2.0 Éclair
    //    API等级6：Android 2.0.1 Éclair
    //    API等级7：Android 2.1 Éclair
    //    API等级8：Android 2.2 - 2.2.3 Froyo  冻酸奶
    //    API等级9：Android 2.3 - 2.3.2 Gingerbread  姜饼
    //    API等级10：Android 2.3.3-2.3.7 Gingerbread
    //    API等级11：Android 3.0 Honeycomb  蜂巢
    //    API等级12：Android 3.1 Honeycomb
    //    API等级13：Android 3.2 Honeycomb
    //    API等级14：Android 4.0 - 4.0.2 Ice Cream Sandwich  雪糕三明治
    //    API等级15：Android 4.0.3 - 4.0.4 Ice Cream Sandwich
    //    API等级16：Android 4.1 Jelly Bean    果冻豆
    //    API等级17：Android 4.2 Jelly Bean
    //    API等级18：Android 4.3 Jelly Bean
    //    API等级19：Android 4.4 KitKat    奇巧
    //    API等级20：Android 4.4W
    //    API等级21：Android 5.0 Lollipop棒棒糖
    //    API等级22：Android 5.1 Lollipop
    //    API等级23：Android 6.0 Marshmallow 棉花糖
    //    API等级24：Android 7.0 Nougat 牛轧糖

    public static void showIme(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideIme(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 判断程序是否已安装
     *
     * @param packagename
     * @return
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        if (context == null || TextUtils.isEmpty(packagename)) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //-------------------------------内存相关 START----------------------------
    //获取应用可申请的最大内存 单位B
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    //获取应用可申请的最大内存 单位M
    public static int getMaxMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass();
    }

    //Large内存  单位 M
    //AndroidMainfest application标签 android:largeHeap="true" 时 getMaxMemory 等于此值
    public static int getLargeMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getLargeMemoryClass();
    }

    //获取CPU数
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 得到设备的可用RAM
     *
     * @return 返回所有内存大小，单位：kb
     */
    public static long getDeviceAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / 1024;
    }

    /**
     * 得到设备的总共RAM
     *
     * @return 返回所有内存大小，单位：kb
     */
    public static long getDeviceTotalMemory(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            br.close();
            long totalMemorySize = Integer.parseInt(memoryLine.replaceAll("\\D+", ""));
            return totalMemorySize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算已使用的内存
     */
    public static long getDeviceUsedMemory(Context context) {
        return getDeviceTotalMemory(context) - getDeviceAvailMemory(context);
    }

    /**
     * 计算已使用内存的百分比
     *
     * @return 单位：kb
     */
    public static int getDeviceUsedPercentValue(Context context) {
        try {
            return (int) (getDeviceUsedMemory(context) * 1.0f / getDeviceTotalMemory(context) * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统正在运行的app
     *
     * @param context
     * @return
     */
    public static List<ActivityManager.RunningAppProcessInfo> getRunningApps(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        return mActivityManager.getRunningAppProcesses();
    }

    /**
     * 关闭正在运行的apps
     *
     * @param context
     * @return object[0] 关闭了几个进程 object[1] 节省了多少内存 单位B object[2] 当前系统内存是多少 单位B
     */
    public static Object[] killRunningApps(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> mRunningProcess = mActivityManager.getRunningAppProcesses();
        return killRunningApps(context, mRunningProcess);
    }

    /**
     * 关闭正在运行的apps
     *
     * @param context
     * @param runningApps
     * @return object[0] 关闭了几个进程 object[1] 节省了多少内存 单位B object[2] 当前系统内存是多少 单位B
     */
    public static Object[] killRunningApps(Context context, List<ActivityManager.RunningAppProcessInfo> runningApps) {
        Object[] result = new Object[]{0, 0, getDeviceUsedMemory(context)};
        if (runningApps == null || runningApps.size() <= 0) {
            return result;
        }
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        long usedMemory = (long) result[2];
        int closeApp = 0;
        String applicationName = context.getApplicationInfo().processName;
        for (ActivityManager.RunningAppProcessInfo amProcess : runningApps) {
            if (amProcess.processName.equals(applicationName) || amProcess.processName.startsWith("system")) {
                //跳过本进程和系统进程
                continue;
            } else {
                closeApp++;
                mActivityManager.killBackgroundProcesses(amProcess.processName);
                LogUtil.e("DeviceUtil", "杀掉的进程：" + amProcess.processName);
            }
        }
        result[0] = closeApp;
        long nowMemory = getDeviceUsedMemory(context);
        result[1] = usedMemory - nowMemory;
        result[2] = nowMemory;
        return result;
    }

    /**
     * 关闭应用
     *
     * @param context
     * @param runningApp
     */
    public static void killRunningApp(Context context, ActivityManager.RunningAppProcessInfo runningApp) {
        if (runningApp == null) {
            return;
        }
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        mActivityManager.killBackgroundProcesses(runningApp.processName);
    }

    //-------------------------------内存相关 END----------------------------
}
