package com.yb.demo.activity.multiprocess.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AidlOtherProcessService extends Service {
    private ArrayList<Game> mGameList = new ArrayList<Game>();
    IClientManager clientManager;
    private IBinder binder = new IGameManager.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Game> getGameList() throws RemoteException {
            return mGameList;
        }

        @Override
        public void addGame(Game game) throws RemoteException {
            mGameList.add(game);
            if (clientManager != null) {
                clientManager.refreshData(mGameList);
            }
            Log.e("test", "add 成功");
        }

        @Override
        public void registerClient(IClientManager cm) throws RemoteException {
            clientManager = cm;
            Log.e("test", "注册成功");
        }


    };

    public AidlOtherProcessService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
