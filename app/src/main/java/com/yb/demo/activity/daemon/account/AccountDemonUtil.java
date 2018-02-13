package com.yb.demo.activity.daemon.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * 启动帐号保活<br>
 * 关于帐号的资料<br>
 * http://blog.csdn.net/lyz_zyx/article/details/73571927
 * https://www.jianshu.com/p/c9337be547ae
 */

public class AccountDemonUtil {
    // TYPE必须与account_service.xml中的TYPE保持一致
    private static final String ACCOUNT_TYPE = "com.yb.demo.demo.account.type";
    private static final String ACCOUNT_NAME = "AccountDemoName";
    private static final String CONTENT_AUTHORITY = AccountProvider.AUTHORITY;
    //30秒同步一次 注意有些手机会禁止自动同步或有最短同步时间，有可能此时间会失效
    private static final long SYNC_FREQUENCY = 30;

    public static void addAccount(Context context) {

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account account = null;
        // 获取系统帐户列表中已添加的帐户是否存在我们的帐户，用TYPE做为标识
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts != null && accounts.length > 0) {
            // 当前系统以有账户
            account = accounts[0];
        } else {
            account = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
        }
        //向系统中添加账户
        if (accountManager.addAccountExplicitly(account, null, null)) {
            //是否可以同步
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            //设置为自动同步
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, new Bundle(), SYNC_FREQUENCY);
            ContentResolver.setMasterSyncAutomatically(true);
            Log.e("test","自动同步已设置");
        }
    }

}
