package com.yb.demo.activity.daemon.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class AccountService extends Service {
    public static final String TAG = "AccountService ";
    private AccountAuthenticator mAuthenticator;

    public AccountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mAuthenticator == null) {
            mAuthenticator = new AccountAuthenticator(this);
        }
        return mAuthenticator.getIBinder();
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
        Log.e("test", TAG + "onDestroy");
        super.onDestroy();
    }

    class AccountAuthenticator extends AbstractAccountAuthenticator {

        public AccountAuthenticator(Context context) {
            super(context);
        }

        @Override
        public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse response, Account account) throws NetworkErrorException {
            //防止账户被删除
            Log.e("test", TAG + "getAccountRemovalAllowed");
            final Bundle result = new Bundle();
            result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
            return result;
        }


        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            Log.e("test", TAG + "editProperties");
            return null;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
            Log.e("test", TAG + "addAccount");
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
            Log.e("test", TAG + "confirmCredentials");
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            Log.e("test", TAG + "getAuthToken");
            return null;
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            Log.e("test", TAG + "getAuthTokenLabel");
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            Log.e("test", TAG + "updateCredentials");
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
            Log.e("test", TAG + "hasFeatures");
            return null;
        }
    }
}
