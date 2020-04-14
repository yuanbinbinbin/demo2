package com.yb.demo.activity.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yb.demo.utils.ActivityUtil;

public class LifeCycleActivityA extends Activity {
    public static final String TAG = "LifeCycleActivityA";
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        tv = new TextView(this);
        tv.setText("LifeCycleActivityA");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startActivity(LifeCycleActivityA.this, LifeCycleActivityB.class);
            }
        });
        tv.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "tv post: " + tv.getMeasuredHeight());
            }
        });
        setContentView(tv);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e(TAG, "onPostCreate " + tv.getMeasuredHeight());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart" + tv.getMeasuredHeight());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume" + tv.getMeasuredHeight());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, tv.getMeasuredHeight() + " onWindowFocusChanged " + hasFocus);
    }
}
