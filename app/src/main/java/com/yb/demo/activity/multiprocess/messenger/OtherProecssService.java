package com.yb.demo.activity.multiprocess.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;

public class OtherProecssService extends Service {
    public OtherProecssService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(mHandler).getBinder();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e("test", "bind success");
                    clientMessenger = msg.replyTo;
                    break;
                case 1:
                    Bundle bundle = msg.getData();
                    Log.e("test", "Service 收到消息: " + bundle.getString("msg"));
                    bundle = new Bundle();
                    bundle.putString("msg", "我收到你的消息拉");
                    Message message = Message.obtain(null, 1);
                    message.setData(bundle);
                    if (clientMessenger != null) {
                        try {
                            clientMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("test","service onDesotry");
    }

    private Messenger clientMessenger;

}
