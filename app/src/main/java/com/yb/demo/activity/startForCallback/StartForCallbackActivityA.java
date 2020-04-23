package com.yb.demo.activity.startForCallback;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class StartForCallbackActivityA extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("start Request");
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartForCallbackManager.startRequest(StartForCallbackActivityA.this, new StartForCallbackManager.ResultCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        Log.e("test123", StartForCallbackActivityA.this+" result: " + result);
                    }

                    @Override
                    public void onFailure() {
                        Log.e("test123", "failure");
                    }
                });
            }
        });
        setContentView(tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("test", "StartForCallbackActivityA destory");
    }
}
