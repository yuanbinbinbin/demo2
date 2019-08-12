package com.base.baselibrary.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yb on 2017/3/9.
 */
public class FileUtil {
    public static final String TAG = "FileUtil";

    /**
     * 在外部存储器上创建私有文件夹<br>
     * app卸载之后，这些文件也会被删除。类似于内部存储。
     * path为"img/cache"时，结果为：/storage/emulated/0/Android/data/PackageName/files/img/cache
     *
     * @param context
     * @param path    "img/cache"
     * @return
     */
    public static File getOutDirPrivate(Context context, String path) {
        if (isExternalPathExist(context)) {//判断是否有权限操作外部存储卡
            File file = new File(context.getExternalFilesDir(path).getAbsolutePath());
            LogUtil.i(TAG, "getOutDirPrivate:" + file.getAbsolutePath());
            if (!file.exists()) {
                file.mkdirs();
                LogUtil.i(TAG, "getOutDirPrivate: file not exist" + file.exists());
            }
            return file;
        }
        return null;
    }

    /**
     * 在外部存储器上创建公共文件夹<br>
     * app卸载之后，这些文件不会被删除，可以被其他app访问。
     * path为"img/cache"时，结果为：/storage/emulated/0/img/cache
     *
     * @param context
     * @param path    "img/cache"
     * @return
     */
    public static File getOutDirPublic(Context context, String path) {
        if (isExternalPathExist(context)) {
            File file = Environment.getExternalStoragePublicDirectory(path);
            LogUtil.i(TAG, "getOutDirPublic:" + file.getAbsolutePath());
            if (!file.exists()) {
                file.mkdirs();
                LogUtil.i(TAG, "getOutDirPublic: file not exist" + file.exists());
            }
            return file;
        }
        return null;
    }

    /**
     * getFilesDir()获取你app的内部存储空间，相当于你的应用在内部存储上的根目录。<br>
     * 存储级别与SharedPreferences一致<br>
     * 一般不使用
     * path 为"img/cache"时，结果：/data/data/PackageName/files/img/cache
     *
     * @param context
     * @param path    "img/cache"
     * @return
     */
    public static File getInnerDir(Context context, String path) {
        File file = new File(context.getFilesDir(), path);
        LogUtil.i(TAG, "getInnerDir:" + file.getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
            LogUtil.i(TAG, "getInnerDir: file not exist" + file.exists());
        }
        return file;
    }

    /**
     * 与getInnerDir 意义相同，目录不同
     * path 为"img/cache"时，结果：/data/data/PackageName/cache/img/cache
     *
     * @param context
     * @param path    "img/cache"
     * @return
     */
    public static File getInnerCacheDir(Context context, String path) {
        File file = new File(context.getCacheDir(), path);
        LogUtil.i(TAG, "getInnerCacheDir:" + file.getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
            LogUtil.i(TAG, "getInnerCacheDir: file not exist " + file.exists());
        }
        return file;
    }

    /**
     * 删除文件或文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteFile(File dir) {
        if (dir != null && dir.exists()) {
            if (dir.isFile()) {
                dir.delete();
            } else if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    deleteFile(dir);
                }
                dir.delete();
            }
        }
        return true;
    }

    /**
     * @param stream
     * @param path
     * @return
     */
    public static boolean writeFile(InputStream stream, String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = stream.read(bytes)) > -1) {
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
        } catch (Exception e) {
            return false;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否可以操作外部存储器
     * 包括权限、判断是否已挂载外部存储器
     *
     * @param context
     * @return
     */
    public static boolean isExternalPathExist(Context context) {
        //Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);判断外部存储器是否挂载
        return haveMountedPermission(context) && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 是否有权限操作存储卡
     *
     * @param context
     * @return
     */
    public static boolean haveMountedPermission(Context context) {
        try {
            String[] array = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            for (String permi : array) {
                if (permi.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
