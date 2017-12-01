package com.yb.demo.activity.md.custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.recyclerviews.BottomSheetDialogRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomBehavior1Activity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_behavior1;
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

    public void move(View view) {
        ViewCompat.offsetTopAndBottom(view, 5);
    }
}
