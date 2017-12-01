package com.yb.demo.activity.image;

import android.app.Dialog;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

public class ImageActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        initCommonView();
        mIvTopBarBack.setVisibility(View.VISIBLE);
        mTvTopBarTitle.setText("Image");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvTopBarBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.id_common_title_bar_back:
                finish();
                break;
        }
    }

}
