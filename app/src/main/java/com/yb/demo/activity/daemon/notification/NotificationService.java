package com.yb.demo.activity.daemon.notification;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Set;

/**
 * 权限需要用户手动开启
 * 部分手机可以开启自启
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("test", "onCreate" + Thread.currentThread());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("test", "onDestroy" + Thread.currentThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test", "onStartCommand");
//        toggleNotificationListenerService(this);
        return super.onStartCommand(intent, flags, startId);
    }


    //当有新通知到来时会回调
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.e("test", "notification Posted" + Thread.currentThread());//main
    }

    //当有通知移除时会回调
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.e("test", "notification Removed" + Thread.currentThread());//main
    }

    //当 NotificationListenerService 是可用的并且和通知管理器连接成功时回调。
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.e("test", "onListenerConnected" + Thread.currentThread());//main
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.e("test", "onListenerDisconnected" + Thread.currentThread());//main
    }

    //检测通知监听服务是否被授权
    public static boolean isNotificationListenerEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }

    //打开通知监听设置页面
    public static void openNotificationListenSettings(Context context) {
        try {
            Intent intent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            } else {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, com.yb.demo.activity.daemon.notification.NotificationService.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(context, com.yb.demo.activity.daemon.notification.NotificationService.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Log.e("test", "toggleNotificationListenerService");
    }


}
