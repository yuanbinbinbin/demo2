package com.base.baselibrary.jni.vm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 检测虚拟机特征文件
 * Created by yb on 2018/3/9.
 */

public class CheckVMUtil {
    static {
        // 加载C代码库, 库的名称, 必须是CMakeLists.txt中指定的名称
        System.loadLibrary("base-lib");
    }

    public static native int check();

    public static native String getCpuInfo();

    public static native String getKernelInfo();


    //获取androidid
    public static String getAndroidID(Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        if ("9774d56d682e549c".equals(ANDROID_ID) || ANDROID_ID == null || ANDROID_ID.length() < 13) {
            ANDROID_ID = new BigInteger(80,new SecureRandom()).toString(16);
            if (ANDROID_ID.charAt(0) == '-')
                ANDROID_ID = ANDROID_ID.substring(1);
            int k = 13 - ANDROID_ID.length();
            if (k > 0)
            {
                StringBuilder localStringBuilder = new StringBuilder();
                while (k > 0)
                {
                    localStringBuilder.append('F');
                    k--;
                }
                localStringBuilder.append(ANDROID_ID);
                ANDROID_ID = localStringBuilder.toString();
            }
        }
        return ANDROID_ID;
    }

    //设备厂商
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    //手机型号 Mumu为网易模拟器
    public static String getDeviceName() {
        return Build.MODEL;
    }

    //获取设备号IMEI 不靠谱
    //<uses-permission android:name="android.permission.READ_PHONE_STATE" />
    // 6.0以上需要动态权限
    public static String getDeviceId(Context act) {
        TelephonyManager telephonyManager = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    //获取CPU频率
    public static int getCpuMaxFrequency() {
        File file = new File("/sys/devices/system/cpu");
        if (!file.exists()) {
            return 0;
        }
        File[] listFiles = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]", pathname.getName());
            }
        });
        if (listFiles == null || listFiles.length <= 0) {
            return 0;
        }
        List arrayList = new ArrayList();
        for (File absolutePath : listFiles) {
            String path = absolutePath.getAbsolutePath();
            try {
                int max = Math.max(Math.max(Integer.parseInt(readFile(path + "/cpufreq/cpuinfo_max_freq")), Integer.parseInt(readFile(path + "/cpufreq/cpuinfo_min_freq"))), Integer.parseInt(readFile(path + "/cpufreq/scaling_cur_freq")));
                if (max > 0) {
                    arrayList.add(Integer.valueOf(max));
                }
            } catch (Throwable th) {
            }
        }
        if (arrayList.isEmpty()) {
            return 0;
        }
        Collections.sort(arrayList);
        return ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
    }

    //获取电池温度
    public static String getBatteryTemp(Context act) {
        if (act == null) {
            return null;
        }
        Intent batteryStatus = act.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (batteryStatus == null) {
            return null;
        }
        int temp = batteryStatus.getIntExtra("temperature", -1);
        if (temp > 0) {
            return tempToStr(((float) temp) / 10.0f, 1);
        }
        return null;
    }

    //获取电池电压
    public static String getBatteryVolt(Context act) {
        if (act == null) {
            return null;
        }
        Intent batteryStatus = act.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (batteryStatus == null) {
            return null;
        }
        int volt = batteryStatus.getIntExtra("voltage", -1);
        if (volt > 0) {
            return String.valueOf(volt);
        }
        return null;
    }

    //是否支持GPS
    public static boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private static String readFile(String str) {
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        if (file.exists()) {
            try {
                String line = null;
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                fileInputStream.close();
                return sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    //温度转换
    private static String tempToStr(float temp, int tempSetting) {
        if (temp <= 0.0f) {
            return "";
        }
        if (tempSetting == 2) {
            return String.format("%.1f°F", new Object[]{Float.valueOf(((9.0f * temp) + 160.0f) / 5.0f)});
        }
        return String.format("%.1f°C", new Object[]{Float.valueOf(temp)});
    }

    //容量转换
    public static String convertSize(int i) {
        int size = i / 1000;
        if (size > 360 && size < 440) {
            return "400M";
        }
        if (size > 460 && size < 540) {
            return "500M";
        }
        if (size > 560 && size < 640) {
            return "600M";
        }
        if (size > 660 && size < 740) {
            return "700M";
        }
        if (size > 760 && size < 840) {
            return "800M";
        }
        if (size > 860 && size < 940) {
            return "900M";
        }
        if (size > 960 && size < 1040) {
            return "1G";
        }
        if (size < 1000) {
            return String.format("%dM", new Object[]{Integer.valueOf(size)});
        }
        return String.format("%.1fG", new Object[]{Float.valueOf(((float) size) / 1000.0f)});
    }
}
