package com.yb.demo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/3/15 19:34
 */
public class ZhuGeUtil {
    private static String uid;
    private static String uidJson;
    private static Context context;
    private static String appKey;
    private static String channel;
    private static String appName;
    private static String appVersion;
    private static String packageName;
    private static String deviceVersion;
    private static String deviceResolution;

    private static void init(Context ctx) {
        context = ctx.getApplicationContext();
        initUid(context);
        initKeyChannel(context);
        initAppInfo(context);
        initDeviceInfo(context);
    }

    public static void createNewUser() {
        if (context == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ak", appKey);//appKey
            jsonObject.put("debug", 0);//debug模式
            jsonObject.put("sln", "itn");//未知
            jsonObject.put("sdk", "zg_android");
            jsonObject.put("owner", "zg");
            jsonObject.put("pl", "and");
            jsonObject.put("sdkv", "3.3.3");
            jsonObject.put("tz", 28800000);//TimeZone
            jsonObject.put("ut", timeStamp2Date(System.currentTimeMillis()));
            jsonObject.put("usr", createDid());

            JSONObject platform = createPlatform();
            JSONObject sessionStart = createSessionStart();
            JSONArray data = new JSONArray();
            data.put(platform);
            data.put(sessionStart);
            jsonObject.put("data", data);
            startUpload(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startUpload(final JSONObject jsonObject) {
        new Thread() {
            @Override
            public void run() {
                final Map<String, Object> postMap = new HashMap<>();
                String data = null;
                try {
                    data = Base64.encodeToString(compress(jsonObject.toString().getBytes("UTF-8")),
                            Base64.DEFAULT).replace("\r", "").replace("\n", "");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                postMap.put("method", "event_statis_srv.upload");
                postMap.put("compress", "1");
                postMap.put("event", data);
                new d().a("https://u.zhugeapi.com/apipool", "https://ubak.zhugeio.com/upload/", postMap);
            }

            private byte[] compress(byte[] var0) {
                byte[] var1 = null;

                try {
                    ByteArrayOutputStream var2 = new ByteArrayOutputStream(var0.length);
                    DeflaterOutputStream var3 = new DeflaterOutputStream(var2);
                    var3.write(var0);
                    var3.finish();
                    var3.close();
                    var1 = var2.toByteArray();
                } catch (Exception var4) {
                    Log.e("com.zhuge.Utils", "compress error", var4);
                }

                return var1;
            }
        }.start();
    }

    private static String timeStamp2Date(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(new Date(time));
    }

    private static JSONObject createDid() throws JSONException {
        String serial = "" + System.currentTimeMillis();
        String board = Build.BOARD;
        String brand = Build.BRAND;
        String cpu = Build.CPU_ABI;
        String device = Build.DEVICE;
        String display = Build.DISPLAY;
        String host = Build.HOST;
        String id = Build.ID;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String tags = Build.TAGS;
        String type = Build.TYPE;
        String user = Build.USER;
        String info = "35" +
                board.length() % 10 + brand.length() % 10 +
                cpu.length() % 10 + device.length() % 10 +
                display.length() % 10 + host.length() % 10 +
                id.length() % 10 + manufacturer.length() % 10 +
                model.length() % 10 + product.length() % 10 +
                tags.length() % 10 + type.length() % 10 +
                user.length() % 10;
        JSONObject object = new JSONObject();
        object.put("did", new UUID(info.hashCode(), serial.hashCode()).toString());
        return object;
    }

    private static JSONObject createPlatform() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("dt", "pl");
        JSONObject pr = new JSONObject();
        pr.put("$cr", "(null)(null)");
        pr.put("$ct", System.currentTimeMillis());
        pr.put("$os", "Android");
        pr.put("$tz", 28800000);
        pr.put("$br", android.os.Build.BRAND);//品牌
        pr.put("$dv", android.os.Build.MODEL);//型号
        pr.put("$lang", "zh");
        pr.put("$mkr", android.os.Build.MANUFACTURER);//生产厂商
        pr.put("$rs", deviceResolution);
        object.put("pr", pr);
        return object;
    }

    private static JSONObject createSessionStart() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("dt", "ss");
        JSONObject pr = new JSONObject();
        pr.put("$sid", System.currentTimeMillis());
        pr.put("$an", appName);//应用名
        pr.put("$cn", channel);//渠道
        pr.put("$cr", "(null)(null)");
        pr.put("$ct", System.currentTimeMillis());
        pr.put("$os", "Android");
        pr.put("$tz", 28800000);
        pr.put("$vn", appVersion);//版本名
        pr.put("$net", "4");
        pr.put("$mnet", "7");
        pr.put("$ov", deviceVersion);
        pr.put("$ss_name", "resu_activity.MainActivity");
        pr.put("$sc", 0);
        pr.put("$ps", packageName);
        object.put("pr", pr);
        return object;
    }

    public static class d {
        private a a = new a();

        public d() {
        }

        private SSLSocketFactory a() {
            SSLSocketFactory var1;
            try {
                SSLContext var2 = SSLContext.getInstance("TLS");
                var2.init((KeyManager[]) null, new X509TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }}, new SecureRandom());
                var1 = var2.getSocketFactory();
            } catch (GeneralSecurityException var3) {
                Log.e("Zhuge.Http", "System has no SSL support.", var3);
                var1 = null;
            }

            return var1;
        }

        public byte[] a(String var1, String var2, Map<String, Object> var3) {
            byte[] var4 = null;
            InputStream var5 = null;
            OutputStream var6 = null;
            BufferedOutputStream var7 = null;
            HttpURLConnection var8 = null;
            int var9 = 0;
            boolean var10 = false;

            while (!var10 && var9 < 3) {
                try {
                    String var11;
                    if (var9 > 1 && var2 != null) {
                        var11 = var2;
                    } else {
                        var11 = var1;
                    }

                    Log.e("ZhugeSDK.Http", "retry " + var9 + " : attempt request to :" + var11);
                    URL var12 = new URL(var11);
                    var8 = (HttpURLConnection) var12.openConnection();
                    if (var8 instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) var8).setSSLSocketFactory(this.a());
                        ((HttpsURLConnection) var8).setHostnameVerifier(this.a);
                    }

                    var8.setConnectTimeout(30000);
                    var8.setReadTimeout(30000);
                    if (null != var3) {
                        Uri.Builder var13 = new Uri.Builder();
                        Iterator var14 = var3.entrySet().iterator();

                        while (var14.hasNext()) {
                            Map.Entry var15 = (Map.Entry) var14.next();
                            var13.appendQueryParameter((String) var15.getKey(), var15.getValue().toString());
                        }

                        byte[] var34 = var13.build().getEncodedQuery().getBytes("UTF-8");
                        var8.setFixedLengthStreamingMode(var34.length);
                        var8.setDoOutput(true);
                        var8.setRequestMethod("POST");
                        var6 = var8.getOutputStream();
                        var7 = new BufferedOutputStream(var6);
                        var7.write(var34);
                        var7.flush();
                        var7.close();
                        var7 = null;
                        var6.close();
                        var6 = null;
                    }

                    var5 = var8.getInputStream();
                    var4 = a(var5);
                    var5.close();
                    var5 = null;
                    var10 = true;
                } catch (Exception var32) {
                    Log.e("ZhugeSDK.Http", "上传数据出错：" + var32.getMessage(), var32);
                    ++var9;
                } finally {
                    if (null != var7) {
                        try {
                            var7.close();
                        } catch (IOException var31) {
                            Log.e("ZhugeSDK.Http", "流关闭出错" + var31.getMessage());
                        }
                    }

                    if (null != var6) {
                        try {
                            var6.close();
                        } catch (IOException var30) {
                            Log.e("ZhugeSDK.Http", "流关闭出错" + var30.getMessage());
                        }
                    }

                    if (null != var5) {
                        try {
                            var5.close();
                        } catch (IOException var29) {
                            Log.e("ZhugeSDK.Http", "流关闭出错" + var29.getMessage());
                        }
                    }

                    if (null != var8) {
                        var8.disconnect();
                    }

                }
            }

            if (var9 >= 3) {
                Log.e("ZhugeSDK.Http", "重连三次仍然出错");
            }

            return var4;
        }

        private static byte[] a(InputStream var0) throws IOException {
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();
            byte[] var3 = new byte[64];

            int var2;
            while ((var2 = var0.read(var3, 0, var3.length)) != -1) {
                var1.write(var3, 0, var2);
            }

            var1.flush();
            return var1.toByteArray();
        }

        class a implements HostnameVerifier {
            a() {
            }

            public boolean verify(String var1, SSLSession var2) {
                return true;
            }
        }
    }

    /**
     * 获取分辨率
     *
     * @param context 上下文
     * @return 设备分辨率
     */
    private static String getResolution(Context context) {
        // user reported NPE in this method; that means either getSystemService or getDefaultDisplay
        // were returning null, even though the documentation doesn't say they should do so; so now
        // we catch Throwable and return empty string if that happens
        StringBuilder resolution = new StringBuilder();
        try {
            final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (wm == null) {
                return resolution.append("null").toString();
            }
            final Display display = wm.getDefaultDisplay();
            final DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            resolution.append(metrics.heightPixels)
                    .append("x")
                    .append(metrics.widthPixels);
        } catch (Exception e) {
            Log.e("ZhugeSDK", "没有检测到分辨率");
        }
        return resolution.toString();
    }

    /**
     * 获取开发者配置信息
     *
     * @param context 应用上下文
     * @return AppKey 与Channel信息组成的字符串，若出错或未填写返回空字符串
     */
    private static void initKeyChannel(Context context) {
        String[] devInfo;
        String ak;
        String cn;
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            appKey = appInfo.metaData.getString("ZHUGE_APPKEY");
            channel = appInfo.metaData.getString("ZHUGE_CHANNEL");
        } catch (Exception e) {
        }
    }

    private static void initAppInfo(Context context) {
        try {
            PackageInfo appInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (appInfo != null) {
                appName = appInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                appVersion = appInfo.versionName;
            }
        } catch (Exception e) {
        }
    }

    private static void initDeviceInfo(Context context) {
        packageName = getMyProcessName(context);
        deviceVersion = android.os.Build.VERSION.RELEASE;
        deviceResolution = getResolution(context);
    }

    /**
     * 获取当前进程名称
     *
     * @return 当前进程名称
     */
    private static String getMyProcessName(Context context) {

        int pid = android.os.Process.myPid();

        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
            if (null == runningAppProcesses) {
                return "";
            }
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    private static void initUid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ZheGeNewPreference", Context.MODE_MULTI_PROCESS);
        uid = sharedPreferences.getString("ZheGeNewPreference_uid", "");
        if (TextUtils.isEmpty(uid)) {
            uid = createUid();
            sharedPreferences.edit().putString("ZheGeNewPreference_uid", uid);
        }
        JSONObject object = new JSONObject();
        try {
            object.put("did", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        uidJson = object.toString();
    }

    private static String createUid() {
        String serial = "" + System.currentTimeMillis();
        String board = Build.BOARD;
        String brand = Build.BRAND;
        String cpu = Build.CPU_ABI;
        String device = Build.DEVICE;
        String display = Build.DISPLAY;
        String host = Build.HOST;
        String id = Build.ID;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String tags = Build.TAGS;
        String type = Build.TYPE;
        String user = Build.USER;
        String info = "35" +
                board.length() % 10 + brand.length() % 10 +
                cpu.length() % 10 + device.length() % 10 +
                display.length() % 10 + host.length() % 10 +
                id.length() % 10 + manufacturer.length() % 10 +
                model.length() % 10 + product.length() % 10 +
                tags.length() % 10 + type.length() % 10 +
                user.length() % 10;
        return new UUID(info.hashCode(), serial.hashCode()).toString();
    }

    public static class Decoder {
        public static String decode(String s){
            String decode = null;
            try {
                decode = new String(decompress(Base64.decode(s.getBytes(), Base64.DEFAULT)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return decode;
        }

        public static byte[] decompress(byte[] bytes) throws IOException {
            if (bytes == null)
                return null;
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            InflaterInputStream iis = new InflaterInputStream(bais, new Inflater(false));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = iis.read(buffer)) != -1) {
                baos.write(buffer, 0, i);
            }
            baos.flush();
            return baos.toByteArray();
        }
    }
}
