package com.yb.demo.activity.daemon.twoservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class DaemonService2 extends Service {
    public DaemonService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test", "DaemonService2 start");
        bindDaemonService1();
        return START_STICKY;
    }

    private void bindDaemonService1() {
        Intent intent = new Intent(this, DaemonService1.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void startDaemonService1() {
        Intent intent = new Intent(this, DaemonService1.class);
        startService(intent);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startDaemonService1();
            bindDaemonService1();
        }
    };

}
