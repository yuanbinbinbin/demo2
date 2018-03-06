package com.yb.demo.activity.daemon;

import android.content.Intent;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.activity.daemon.account.AccountDemonUtil;
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
}
