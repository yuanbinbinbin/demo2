package com.yb.demo.activity.multiprocess.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.util.List;

public class AidlProcessActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_process2;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    public void connect(View v) {
        Intent intent = new Intent(getContext(), AidlOtherProcessService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            gameManager = IGameManager.Stub.asInterface(service);
            try {
                gameManager.registerClient(clientManager);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("test", "连接成功..");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    IGameManager gameManager;

    public void add(View v) {
        if (gameManager != null) {
            try {
                gameManager.addGame(new Game("CF", "穿越火线"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void get(View v) {
        if (gameManager != null) {
            try {
                Log.e("test", "list: " + gameManager.getGameList());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    IClientManager clientManager = new IClientManager.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void refreshData(List<Game> data) throws RemoteException {
            Log.e("test", "refresh: " + data);
        }
    };
}
