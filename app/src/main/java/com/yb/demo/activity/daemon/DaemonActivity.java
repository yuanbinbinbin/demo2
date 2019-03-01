package com.yb.demo.activity.daemon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.activity.daemon.account.AccountDemonUtil;
import com.yb.demo.activity.daemon.c.NativeDemonService;
import com.yb.demo.activity.daemon.jobservice.DaemonService;
import com.yb.demo.activity.daemon.jobservice.JobServicActivity;
import com.yb.demo.activity.daemon.notification.NotificationService;
import com.yb.demo.activity.daemon.twoservice.TwoServiceActivity;
import com.yb.demo.utils.ActivityUtil;

public class DaemonActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daemon;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    //JobService 保活
    public void jobService(View v) {
        ActivityUtil.startActivity(getContext(), JobServicActivity.class);
    }

    //双进程保活
    public void twoService(View v) {
        ActivityUtil.startActivity(getContext(), TwoServiceActivity.class);
    }

    //Account保活
    public void accountDemo(View v) {
        AccountDemonUtil.addAccount(this);
    }

    //notificationDemo保活
    public void notificationDemo(View v) {
        if (NotificationService.isNotificationListenerEnabled(this)) {
            Intent intent = new Intent(this, NotificationService.class);
            startService(intent);
        } else {
            NotificationService.openNotificationListenSettings(this);
        }
    }

    //native保活
    public void nativeDemo(View view) {
        Intent intent = new Intent(this, NativeDemonService.class);
        startService(intent);
    }

    //AlarmManager保活
    public void alarm(View view) {
        Intent intent = new Intent(getContext(), DaemonService.class);
        PendingIntent pi = PendingIntent.getService(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        //设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
        // 5秒后通过PendingIntent pi对象发送广播
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), 2 * 1000, pi);
    }
}
