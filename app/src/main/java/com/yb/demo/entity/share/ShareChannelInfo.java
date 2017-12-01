package com.yb.demo.entity.share;

/**
 * Created by yb on 2017/11/16.
 */
public class ShareChannelInfo {
    String packageName;
    String appId;

    public ShareChannelInfo(String packageName, String appId) {
        this.packageName = packageName;
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
