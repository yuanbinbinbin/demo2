package com.yb.demo.activity.touch;

import android.view.MotionEvent;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

public class TouchActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_touch;
    }

    @Override
    protected void initView() {
        View viewGroup = findViewById(R.id.touchGroup);
        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        View view1 = findViewById(R.id.touchView1);
        view1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        View view2 = findViewById(R.id.touchView2);
        view2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
