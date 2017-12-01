package com.yb.demo.activity.multiprocess.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

public class ProcessActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_process;
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

    public void startService(View v) {
        Intent intent = new Intent(getContext(), OtherProecssService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void sendMessage(View v) {
        if(mMessenger != null){
            Message message = Message.obtain(null,1);
            Bundle bundle = new Bundle();
            bundle.putString("msg","Hello,我是客户端");
            message.setData(bundle);
            try {
                mMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Message message = Message.obtain(null,0);
            message.replyTo = new Messenger(mHandler);
            try {
                mMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    Log.e("test", "收到Service消息：" + bundle.getString("msg"));
                    break;
            }
        }
    };

    private Messenger mMessenger;
}
