package com.yb.demo.activity.emulatorcheck;


import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.base.baselibrary.jni.vm.CheckVMUtil;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.util.Map;

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
        sb.append("sdk_Level:");
        sb.append(CheckVMUtil.getSDKLevel());
        sb.append("\n");
        sb.append("设备厂商:");
        sb.append(brand);
        sb.append("\n");
        sb.append("设备型号:");
        sb.append(name);
        sb.append("\n");
        sb.append("android_id:");
        sb.append(CheckVMUtil.getAndroidID(this));
        sb.append("\n");


        Map<String, String> imeis = CheckVMUtil.getDeviceIds(this);
        if (imeis != null && imeis.size() > 0) {
            for (String key : imeis.keySet()) {
                sb.append(key);
                sb.append(":");
                sb.append(imeis.get(key));
                sb.append("\n");
            }
        }

        String wifiName = CheckVMUtil.getWifiName(this);
        if (wifiName != null) {
            sb.append("wifi SSID:");
            sb.append(":");
            sb.append(wifiName);
            sb.append("\n");
        }

        String BSSID = CheckVMUtil.getBSSID(this);
        if (BSSID != null) {
            sb.append("wifi BSSID:");
            sb.append(":");
            sb.append(BSSID);
            sb.append("\n");
        }

        String wifiMac = CheckVMUtil.getWifiMacAddress(this);
        if (wifiMac != null) {
            sb.append("wifi MAC:");
            sb.append(":");
            sb.append(wifiMac);
            sb.append("\n");
        }

        String bluetoothAddress = CheckVMUtil.getBluetoothAddress(this);
        if (bluetoothAddress != null) {
            sb.append("蓝牙 Address:");
            sb.append(":");
            sb.append(bluetoothAddress);
            sb.append("\n");
        }
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
