package com.yb.demo.activity.daemon.twoservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class DaemonService1 extends Service {
    public DaemonService1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test", "DaemonService1 start");
        bindDaemonService2();
        return START_STICKY;
    }
    private void bindDaemonService2() {
        Intent intent = new Intent(this, DaemonService2.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void startDaemonService2() {
        Intent intent = new Intent(this, DaemonService2.class);
        startService(intent);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startDaemonService2();
            bindDaemonService2();
        }
    };
}
