package com.yb.demo.activity.animations;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.weights.bezier.WaveView;

public class WaveActivity extends BaseActivity {
    private WaveView waveView0;
    private WaveView waveView;
    private WaveView waveView2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wave;
    }

    @Override
    protected void initView() {
        waveView0 = (WaveView) findViewById(R.id.id_activity_wave0);

        waveView = (WaveView) findViewById(R.id.id_activity_wave);
        waveView.setDstBitmap(R.mipmap.ic_launcher);

        waveView2 = (WaveView) findViewById(R.id.id_activity_wave2);
        waveView2.setDstBitmap(R.drawable.loading2);
        waveView2.setWaterColor(0xFF20B2AA);
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
            waveView0.start();
            waveView.start();
            waveView2.start();
        }
    }

    public void end(View v) {
        if (waveView0 != null && waveView0.isRunning()) {
            waveView0.stop();
        }
        if (waveView != null && waveView.isRunning()) {
            waveView.stop();
        }
        if (waveView2 != null && waveView2.isRunning()) {
            waveView2.stop();
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
            waveView0.updateWaveHeight(percent);
            waveView.updateWaveHeight(percent);
            waveView2.updateWaveHeight(percent);
            if (percent < 1) {
                mHandler.postDelayed(runnable, 1000);
            }
        }
    };
    float percent = 0f;
}
