package com.yb.demo.activity;

import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.utils.checkEmulator.Check;
import com.yb.demo.utils.checkEmulator.JniAnti;
import com.yb.demo.utils.checkEmulator.Util;

public class EmulatorCheckActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emulator_check;
    }

    @Override
    protected void initView() {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        GLSurfaceView gl = (GLSurfaceView) findViewById(R.id.hwGPU);
        gl.setRenderMode(0);  //此处是为了加载显卡信息
//        Log.e("qtfreet000", "APK签名：" + JniAnti.getApkSign());
        Log.e("qtfreet000", "程序包名：" + Check.getPackageName(this));
        Log.e("qtfreet000", "CPU信息：" + JniAnti.getCpuinfo());
        Log.e("qtfreet000", "CPU频率：" + Check.getCpuFrequency());
        Log.e("qtfreet000", "CPU核心数量：" + Check.getCpuCore());
        Log.e("qtfreet000", "内核信息：" + JniAnti.getKernelVersion());
        Log.e("qtfreet000", "设备ID：" + JniAnti.getDeviceID());
        Log.e("qtfreet000", "已安装App：" + Check.getInstalledApps(this));
        Log.e("qtfreet000", "MAC地址：" + Check.getMacAddress(this));
        Log.e("qtfreet000", "内存大小：" + Check.getMemorySize());
        // Log.e("qtfreet000", "存在重力感应器：" + Check.checkGravity(this));  //这点不靠谱，很多手机还是检测不出来
        Log.e("qtfreet000", "设备厂商：" + Check.getModelBrand());
        Log.e("qtfreet000", "设备型号：" + Check.getModelName());
        Log.e("qtfreet000", "支持GPS：" + Check.hasGPSDevice(this));
        Log.e("qtfreet000", "支持多点触控：" + Check.checkMultiTouch(this));
        Log.e("qtfreet000", "电池温度：" + Check.getBatteryTemp(this));
        Log.e("qtfreet000", "电池电压：" + Check.getBatteryVolt(this));
        Log.e("qtfreet000", "模拟器特征数量：" + JniAnti.checkAntiFile());


        String cpu = JniAnti.getCpuinfo();
        String cpuFreq = Util.convertSize(Check.getCpuMaxFrequency());
        String kernel = JniAnti.getKernelVersion();
        boolean gravity = Check.checkGravity(this);
        String temp = Check.getBatteryTemp(this);
        String volt = Check.getBatteryVolt(this);
        int check = JniAnti.checkAntiFile();
        boolean gps = Check.hasGPSDevice(this);
        StringBuilder sb = new StringBuilder();
        if (cpu.contains("Genuine Intel(R)") || cpu.contains("Intel(R) Core(TM)") || cpu.contains("Intel(R) Pentium(R)") || cpu.contains("Intel(R) Xeon(R)") || cpu.contains("AMD")) {
            sb.append("特征一：" + cpu + "\n");
        }
        if (kernel.contains("qemu+") || kernel.contains("tencent") || kernel.contains("virtualbox")) {
            sb.append("特征二：" + kernel + "\n");
        }
        if (gravity == false) {
            sb.append("特征三：" + "无重力感应器\n");
        }
        if (TextUtils.isEmpty(temp)) {
            sb.append("特征四：" + "无电池温度\n");
        }
        if (TextUtils.isEmpty(volt)) {
            sb.append("特征五：" + "无电池电压\n");
        }
        if (check > 0) {
            sb.append("特征六：" + "模拟器特征文件\n");
        }
        if (gps == false) {
            sb.append("特征七：" + "无gps\n");
        }
        if (cpuFreq.equals("0M")) {
            sb.append("特征八：" + "cpu无频率\n");
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
