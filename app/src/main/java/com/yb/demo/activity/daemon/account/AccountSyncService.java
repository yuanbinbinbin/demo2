package com.yb.demo.activity.daemon.account;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

/**
 * 帐号同步会激活此service
 */
public class AccountSyncService extends Service {
    public static final String TAG = "AccountSyncService";
    private AccountSyncAdapter accountSyncAdapter;

    public AccountSyncService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (accountSyncAdapter == null) {
            accountSyncAdapter = new AccountSyncAdapter(getApplicationContext(), true);
        }
        return accountSyncAdapter.getSyncAdapterBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("test", TAG + "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test", TAG + "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("test", TAG + "onDestroy");
    }

    class AccountSyncAdapter extends AbstractThreadedSyncAdapter {

        public AccountSyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            Log.e("test", "帐号同步---------");
        }
    }
}
