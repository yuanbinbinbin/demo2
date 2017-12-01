package com.yb.demo.activity.animations;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.weights.bezier.WaveView;

public class WaveActivity extends BaseActivity {
    private WaveView waveView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wave;
    }

    @Override
    protected void initView() {
        waveView = (WaveView) findViewById(R.id.id_activity_wave);
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
        end(null);
    }

    public void start(View v) {
        if (!waveView.isRunning()) {
            percent = 0;
            mHandler.postDelayed(runnable, 1000);
            waveView.start();
        }
    }

    public void end(View v) {
        if (waveView != null && waveView.isRunning()) {
            waveView.stop();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            percent += 0.02f;
            waveView.updateWaveHeight(percent);
            if (percent < 1) {
                mHandler.postDelayed(runnable, 1000);
            }
        }
    };
    float percent = 0f;
}
