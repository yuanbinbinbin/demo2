# Android Daemon

# account
#### 账户同步保活


# c 
#### Native 5.0以下保活
<img src="https://github.com/yuanbinbinbin/demo2/blob/master/onlinepic/native_daemon.gif" alt="native_daemon.gif" />


# jobservice
#### JobScheduler 5.0以上保活

# notification
#### 通过监听通知栏 保活 API >= 18
缺点：需要用户手动设置权限<br>
部分手机可实现开机自启效果

# twoservice
#### 两个service互相监听保活
效果很不好
  
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
  
