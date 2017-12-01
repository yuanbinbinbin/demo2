package com.yb.demo.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.demo.R;
import com.yb.demo.utils.changeStatusBarColor.StatusBarUtil;
import com.yb.demo.weights.ColorPicker;

public class ChangeStatusColorActivity extends AppCompatActivity {
    
    private Context mContext;
    private ColorPicker mColorPicker;
    private Button mButton;
    private TextView mTitle;
    private RadioGroup mRadioGroup;
    private RadioGroup mRadioGroupConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status_color);
        mContext = this;

        mColorPicker = (ColorPicker) findViewById(R.id.color_picker);
        mTitle = (TextView) findViewById(R.id.tv_title);

        mRadioGroup = (RadioGroup) findViewById(R.id.rgroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                String message = "";
                RadioButton radioButton = null;
                if (checkedRadioButtonId == R.id.rb_statusbar_white) {
                    StatusBarUtil.StatusBarLightMode(ChangeStatusColorActivity.this, false);
                } else {
                    StatusBarUtil.StatusBarLightMode(ChangeStatusColorActivity.this, true);
                }
                radioButton = (RadioButton) ChangeStatusColorActivity.this.findViewById(checkedRadioButtonId);
                message = radioButton.getText().toString();
                Toast.makeText(ChangeStatusColorActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        mRadioGroupConfiguration = (RadioGroup) findViewById(R.id.rgroup_configuration);
        mRadioGroupConfiguration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_landscape){//横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
                    StatusBarUtil.setFitsSystemWindows(ChangeStatusColorActivity.this, true);
                }else if(checkedId == R.id.rb_portrait){//竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//清除全屏
                    StatusBarUtil.setFitsSystemWindows(ChangeStatusColorActivity.this, false);
                }
            }
        });
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applySelectedColor();
            }
        });
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorAccent), false);
        mTitle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        applySelectedColor();
    }

    private void applySelectedColor() {
        int selected = mColorPicker.getColor();
//        int color = Color.argb(153, Color.red(selected), Color.green(selected), Color.blue(selected));
        int color = Color.rgb(Color.red(selected), Color.green(selected), Color.blue(selected));

        StatusBarUtil.setStatusBarColor(this, color, false);
        mTitle.setBackgroundColor(color);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
//            Log.e(TAG, "onConfigurationChanged: " + "横屏");
//            onConfigurationLandScape();

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Log.e(TAG, "onConfigurationChanged: " + "竖屏");
//            onConfigurationPortrait();
        }
    }
}
