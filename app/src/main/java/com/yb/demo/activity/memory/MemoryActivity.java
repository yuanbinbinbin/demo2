package com.yb.demo.activity.memory;

import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.ActivityUtil;

public class MemoryActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_memory;
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

    public void memoryInfo(View view) {
        ActivityUtil.startActivity(this, MemoryInfoActivity.class);
    }
}
