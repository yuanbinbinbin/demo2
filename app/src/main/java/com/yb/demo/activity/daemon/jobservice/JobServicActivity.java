package com.yb.demo.activity.daemon.jobservice;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.DeviceUtil;

public class JobServicActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_servic;
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

    public void start(View v) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Intent intent = new Intent(getContext(), DaemonService.class);
        startService(intent);
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

//        <service
//        android:name=".activity.daemon.jobservice.MyJobService"
//        android:permission="android.permission.BIND_JOB_SERVICE"/>
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()));
        builder.setPeriodic(3000);//每隔3秒执行一次
//        builder.setMinimumLatency(1000);//设置任务的延迟执行时间(单位是毫秒),这个函数与setPeriodic(long time)方法不兼容，如果这两个方法同时调用了就会引起异常
//        builder.setOverrideDeadline(2000);//设置任务最晚的延迟时间(单位是毫秒),与setMinimumLatency(long time)一样，这个方法也会与setPeriodic(long time)，同时调用这两个方法会引发异常。
        builder.setPersisted(true);//告诉系统当你的设备重启之后你的任务是否还要继续执行，需要<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
        //这个方法让你这个任务只有在满足指定的网络条件时才会被执行。
        // 默认条件是JobInfo.NETWORK_TYPE_NONE，这意味着不管是否有网络这个任务都会被执行。另外两个可选类型，
        // 一种是JobInfo.NETWORK_TYPE_ANY，它表明需要任意一种网络才使得任务可以执行。
        // 另一种是JobInfo.NETWORK_TYPE_UNMETERED，它表示设备不是蜂窝网络( 比如在WIFI连接时 )时任务才会被执行
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        builder.setRequiresCharging(false);//这个方法告诉你的应用，只有当设备在充电时这个任务才会被执行。
        builder.setRequiresDeviceIdle(false);//zhe个方法告诉你的任务只有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务。

        if (scheduler.schedule(builder.build()) <= 0) {
            Log.e("test", "JobService 启动失败");
        } else {
            Log.e("test", "JobService 启动成功");
        }
//        mJobScheduler.cancelAll();取消所有
    }
}
