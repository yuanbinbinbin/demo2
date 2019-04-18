package com.base.baselibrary.utils.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * desc:<br>
 * author : yuanbin<br>
 * date : 2019/1/14 11:46
 */
public class BaseFileProviderUtils {
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".koala.fileProvider", file);
        return fileUri;
    }
}
