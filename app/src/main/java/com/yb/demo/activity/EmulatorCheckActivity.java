package com.yb.demo.activity;


import android.text.TextUtils;
import android.widget.TextView;

import com.base.baselibrary.jni.vm.CheckVMUtil;
import com.yb.demo.R;

public class EmulatorCheckActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emulator_check;
    }

    @Override
    protected void initView() {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        String brand = CheckVMUtil.getDeviceBrand();
        String name = CheckVMUtil.getDeviceName();

        String cpu = CheckVMUtil.getCpuInfo();
        String cpuFreq = CheckVMUtil.convertSize(CheckVMUtil.getCpuMaxFrequency());
        String kernel = CheckVMUtil.getKernelInfo();
        String temp = CheckVMUtil.getBatteryTemp(this);
        String volt = CheckVMUtil.getBatteryVolt(this);
        int check = CheckVMUtil.check();
        boolean gps = CheckVMUtil.hasGPSDevice(this);
        StringBuilder sb = new StringBuilder();
        sb.append("设备厂商:");
        sb.append(brand);
        sb.append("\n");
        sb.append("设备型号:");
        sb.append(name);
        sb.append("\n");
        sb.append("android_id:");
        sb.append(CheckVMUtil.getAndroidID(this));
        sb.append("\n");

        sb.append("uuid:");
        sb.append(CheckVMUtil.getAndroidID(this));
        sb.append("\n");

        if (cpu.contains("Genuine Intel(R)") || cpu.contains("Intel(R) Core(TM)") || cpu.contains("Intel(R) Pentium(R)") || cpu.contains("Intel(R) Xeon(R)") || cpu.contains("AMD")) {
            sb.append("特征1：" + cpu + "\n");
        }
        if (kernel.contains("qemu+") || kernel.contains("tencent") || kernel.contains("virtualbox")) {
            sb.append("特征2：" + kernel + "\n");
        }
        if (TextUtils.isEmpty(temp)) {
            sb.append("特征3：" + "无电池温度\n");
        }
        if (TextUtils.isEmpty(volt)) {
            sb.append("特征4：" + "无电池电压\n");
        }
        if (check > 0) {
            sb.append("特征5：" + "模拟器特征文件数:" + check + "\n");
        } else {
            sb.append("模拟器特征数：" + check + "\n");
        }
        if (gps == false) {
            sb.append("特征6：" + "无gps\n");
        }
        if (cpuFreq.equals("0M")) {
            sb.append("特征7：" + "cpu无频率\n");
        }
        tv.setText(sb.toString());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
