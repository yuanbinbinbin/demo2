package com.yb.demo.activity.daemon.jobservice;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent intent = new Intent(this, DaemonService.class);
        startService(intent);
        Log.e("test", "MyJobService start Job");
        //返回false表示执行完毕，返回true表示需要开发者自己调用jobFinished方法通知系统已执行完成
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
