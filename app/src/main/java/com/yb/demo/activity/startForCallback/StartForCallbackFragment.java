package com.yb.demo.activity.startForCallback;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class StartForCallbackFragment extends Fragment {

    private StartForCallbackManager.ResultCallback mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置fragment保留，已保留的fragment（在异常情况下）不会随着activity一起销毁，相反，它会一直保留（进程不消亡的前提下）
        //比如在设备旋转的情况下，该fragment可暂时与Activity分离，等activity重新创建后，会将该fragment与activity重新绑定，fragment数据不会丢失
        //如果在恰好在分离的那段时间使用Context信息，就可能会出错，这时可以使用isAdded()判断Fragment是否绑定到Activity

        //如果Activity正常销毁，fragment也会销毁
        setRetainInstance(true);
        Log.e("test", getActivity() + " StartForCallbackFragment onCreate");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void request(StartForCallbackManager.ResultCallback callback) {
        mCallback = callback;
        Intent intent = new Intent(getActivity(), StartForCallbackActivityB.class);
        startActivityForResult(intent, 1998);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("test", getActivity() + " StartForCallbackFragment : onActivityResult: " + mCallback);
        if (requestCode == 1998) {
            if (mCallback != null) {
                if (resultCode == Activity.RESULT_OK) {
                    mCallback.onSuccess(data.getIntExtra("result", 0));
                } else {
                    mCallback.onFailure();
                }
            }
        }
        StartForCallbackManager.remove(getActivity());
    }
}
