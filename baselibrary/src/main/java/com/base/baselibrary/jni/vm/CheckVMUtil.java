package com.base.baselibrary.jni.vm;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            ANDROID_ID = new BigInteger(80, new SecureRandom()).toString(16);
            if (ANDROID_ID.charAt(0) == '-')
                ANDROID_ID = ANDROID_ID.substring(1);
            int k = 13 - ANDROID_ID.length();
            if (k > 0) {
                StringBuilder localStringBuilder = new StringBuilder();
                while (k > 0) {
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

    //获取版本号
    public static int getSDKLevel() {
        return Build.VERSION.SDK_INT;
    }

    //获取设备号IMEI 不靠谱
    //<uses-permission android:name="android.permission.READ_PHONE_STATE" />
    // 6.0以上需要动态权限
    //MEID CDMA 电信
    //IMEI 移动联通
    //注意：全网通手机Phone在初始化时只创建了GSMPhone，并没有创建CDMAPhone，此时是无法获取到meid。只有插入CDMA卡插入后才会显示
    public static Map<String, String> getDeviceIds(Context act) {
        Map<String, String> result = new HashMap<String, String>();

        TelephonyManager telephonyManager = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // < 21 5.0
            String imeiOrMeid = telephonyManager.getDeviceId();
            if (imeiOrMeid != null) {
                if (14 == imeiOrMeid.length()) {
                    result.put("meid", imeiOrMeid);
                } else {
                    result.put("imei", imeiOrMeid);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // >= 21 5.0
            Class<?> clazz = null;
            Method method = null;
            try {
                clazz = Class.forName("android.os.SystemProperties");
                method = clazz.getMethod("get", String.class, String.class);
                String gsm = (String) method.invoke(null, "ril.gsm.imei", "");
                String meid = (String) method.invoke(null, "ril.cdma.meid", "");
                if (meid != null && !"".equals(meid)) {
                    result.put("meid", meid);
                }
                if (gsm != null && !"".equals(gsm)) {
                    //the value of gsm like:xxxxxx,xxxxxx
                    String imeiArray[] = gsm.split(",");
                    if (imeiArray != null && imeiArray.length > 0) {
                        result.put("imei1", imeiArray[0]);

                        if (imeiArray.length > 1) {
                            result.put("imei2", imeiArray[1]);
                        } else {
                            result.put("imei2", telephonyManager.getDeviceId(1));
                        }
                    } else {
                        result.put("imei1", telephonyManager.getDeviceId(0));
                        result.put("imei2", telephonyManager.getDeviceId(1));
                    }
                } else {
                    result.put("imei1", telephonyManager.getDeviceId(0));
                    result.put("imei2", telephonyManager.getDeviceId(1));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static WifiInfo getWifiInfo(Context act) {
        try {
            WifiInfo localWifiInfo = ((WifiManager) act.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
            if (((ConnectivityManager) act.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                String name = localWifiInfo.getSSID();
                if (name != null) {
                    return localWifiInfo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取wifi名
    //<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    public static String getWifiName(Context act) {
        WifiInfo info = getWifiInfo(act);
        return info == null ? null : info.getSSID();
    }

    public static String getBSSID(Context act) {
        WifiInfo info = getWifiInfo(act);
        return info == null ? null : info.getBSSID();
    }

    public static String getWifiMacAddress(Context act) {
        WifiInfo info = getWifiInfo(act);
        if (info != null) {
            String mac = info.getMacAddress();
            if (null == mac || "".equals(mac) || "02:00:00:00:00:00".equals(mac)) {
                try {
                    Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
                    String str1;
                    try {
                        Class localClass = Class.forName("android.os.SystemProperties");
                        str1 = (String) localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localClass, new Object[]{"wifi.interface", "wlan0"});
                    } catch (Exception e) {
                        str1 = "waln0";
                    }
                    while (localEnumeration.hasMoreElements()) {
                        NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration.nextElement();
                        byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
                        if ((arrayOfByte != null) && (arrayOfByte.length != 0)) {
                            StringBuilder localStringBuilder = new StringBuilder();
                            int i = arrayOfByte.length;
                            for (int j = 0; j < i; j++) {
                                byte b = arrayOfByte[j];
                                Object[] arrayOfObject = new Object[1];
                                arrayOfObject[0] = Byte.valueOf(b);
                                localStringBuilder.append(String.format("%02X:", arrayOfObject));
                            }
                            if (localStringBuilder.length() > 0)
                                localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
                            String str2 = localStringBuilder.toString();
                            boolean bool = localNetworkInterface.getName().equals(str1);
                            if (bool)
                                mac = str2;
                        }
                    }
                    return mac;
                } catch (Exception e) {
                    return mac;
                }
            }
        }
        return null;
    }

    //获取蓝牙mac地址
    public static String getBluetoothAddress(Context act) {
        try {
            BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (localBluetoothAdapter != null) {
                if (localBluetoothAdapter.isEnabled()) {
                    String result = localBluetoothAdapter.getAddress();
                    if (!"02:00:00:00:00:00".equals(result))
                        return result;
                    return Settings.Secure.getString(act.getContentResolver(), "bluetooth_address");
                }
            }
        } catch (Exception e) {
        }
        return null;
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
