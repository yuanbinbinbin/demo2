package com.base.baselibrary;

import android.app.Activity;
import android.app.Application;

/**
 * 全局常量
 * Created by yb on 2017/8/16.
 */
public class BaseGlobalVariable {
    private static Application mApp = null;

    public static void init(Activity mActivity) {
        if (mActivity != null) {
            mApp = mActivity.getApplication();
        }
    }

    /**
     * 全局获取Application
     */
    public static Application getApplication() {
        return mApp;
    }
}
