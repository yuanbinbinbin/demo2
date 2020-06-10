package com.yb.demo.activity.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yb.demo.R;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }

    public void layoutChild(View v) {
//        ViewGroup.LayoutParams params = v.getLayoutParams();
//        params.height = 50;
        v.requestLayout();
    }

    public void invalidateChild(View v) {
        v.invalidate();
    }


}
