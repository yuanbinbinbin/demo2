package com.yb.demo.activity.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.DeviceUtil;

public class MemoryInfoActivity extends BaseActivity {

    private TextView mTvInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_memory_info;
    }

    @Override
    protected void initView() {
        mTvInfo = (TextView) findViewById(R.id.id_activity_memory_info);
    }

    @Override
    protected void initData() {
        StringBuilder sb = new StringBuilder();
        sb.append("CPU数量：");
        sb.append(DeviceUtil.getAvailableProcessors());
        sb.append("\n");

        sb.append("本应用信息:\n");
        sb.append("Runtime方式:\n");
        sb.append("可申请的最大内存:");
        sb.append(DeviceUtil.getMaxMemory() / 1024 / 1024);
        sb.append("M\n");

        sb.append("\n");
        sb.append("ActivityManager.MemoryInfo方式:\n");
        sb.append("可申请的最大内存:");
        sb.append(DeviceUtil.getMaxMemory(this));
        sb.append("M\n");

        sb.append("Large:");
        sb.append(DeviceUtil.getLargeMemory(this));
        sb.append("M\n");

        sb.append("\n");
        sb.append("上面的值存储在/system/build.prop文件中\n" +
                "dalvik.vm.heapstartsize=8m    ----起始分配内存\n" +
                "dalvik.vm.heapgrowthlimit=192m ---- 一般情况app申请的最大内存 \n" +
                "dalvik.vm.heapsize=512m   ---- 设置largeheap时，App可用的最大内存\n" +
                "dalvik.vm.heaptargetutilization=0.75  ---- 内存利用率 GC时向该值靠拢\n" +
                "dalvik.vm.heapminfree=512k  ----可用内存最少为512k GC相关\n" +
                "dalvik.vm.heapmaxfree=8m     -----可用内存最大为8M GC机制相关\n");

        //largeHeap false  maxMeory   largeHeap true
        //i9300  4.4.4      64         256
        //A7000  6.0.1      192        512
        //vivoX6D 5.1       256         512

        sb.append("\n");
        sb.append("系统信息:\n");
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        //API16 4.1、4.1.1及以上
        sb.append("第一种方式:\n");
        sb.append("系统总内存:");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sb.append((info.totalMem / (1024 * 1024)));
        } else {
            sb.append("0");
        }
        sb.append("M(此方法需要在API16 4.1、4.1.1及以上 才可实现)\n");

        sb.append("系统已使用内存:");
        sb.append(info.availMem / (1024 * 1024));
        sb.append("M\n");

        sb.append("系统剩余内存:");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sb.append((info.totalMem - info.availMem) / (1024 * 1024));
        } else {
            sb.append("0");
        }
        sb.append("M\n");

        sb.append("系统是否处于低内存运行：");
        sb.append(info.lowMemory);
        sb.append("\n");

        sb.append("系统剩余内存低于：");
        sb.append((info.threshold / (1024 * 1024)));
        sb.append("M时为低内存运行\n");


        sb.append("\n");
        sb.append("第二种方式:\n");
        sb.append("系统总内存:");
        sb.append((DeviceUtil.getDeviceTotalMemory(this) / (1024)));
        sb.append("M\n");

        sb.append("系统已使用内存:");
        sb.append((DeviceUtil.getDeviceAvailMemory(this) / (1024)));
        sb.append("M\n");

        sb.append("系统剩余内存:");
        sb.append((DeviceUtil.getDeviceUsedMemory(this) / (1024)));
        sb.append("M\n");
        mTvInfo.setText(sb.toString());
    }

    @Override
    protected void initListener() {

    }
}
