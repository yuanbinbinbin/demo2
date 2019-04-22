package com.yb.videolibrary.video;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.System;
import android.widget.Toast;

class LightController {

    // 判断是否开启了自动亮度调节
    public static boolean isAutoBrightness(Context act) {
        boolean automicBrightness = false;
        ContentResolver aContentResolver = act.getContentResolver();
        try {
            automicBrightness = System.getInt(aContentResolver,
                    System.SCREEN_BRIGHTNESS_MODE) == System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Exception e) {
            Toast.makeText(act, "无法获取亮度", Toast.LENGTH_SHORT).show();
        }
        return automicBrightness;
    }

    // 改变亮度
    public static void setLightness(Context act, int value) {
        try {
            if (!checkPermission(act)) {
                return;
            }
            if (isAutoBrightness(act)) {
                stopAutoBrightness(act);
            }
            System.putInt(act.getContentResolver(), System.SCREEN_BRIGHTNESS, value);
        } catch (Exception e) {
            Toast.makeText(act, "无法改变亮度", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean checkPermission(Context act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果当前平台版本大于23平台
            if (!Settings.System.canWrite(act)) {
                //如果没有修改系统的权限这请求修改系统的权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + act.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(intent);
                return false;
            }
        }
        return true;
    }

    // 获取亮度
    public static int getLightness(Context act) {
        return System.getInt(act.getContentResolver(), System.SCREEN_BRIGHTNESS, -1);
    }

    // 停止自动亮度调节
    public static void stopAutoBrightness(Context activity) {
        System.putInt(activity.getContentResolver(),
                System.SCREEN_BRIGHTNESS_MODE,
                System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    // 开启亮度自动调节
    public static void startAutoBrightness(Context activity) {
        System.putInt(activity.getContentResolver(),
                System.SCREEN_BRIGHTNESS_MODE,
                System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }
}
