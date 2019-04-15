package com.yb.demo.application;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/4/12 18:59
 */
public class TinkerImplApplication extends TinkerApplication {
    public TinkerImplApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.yb.demo.application.YbApplication",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
