package com.yb.demo.activity.animations;

import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.listviews.animations.DotAdapter;

import java.util.ArrayList;
import java.util.List;

public class DotActivity extends BaseActivity {

    private ListView mLv;
    private DotAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dot;
    }

    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.id_activity_dot_lv);
    }

    @Override
    protected void initData() {
        mAdapter = new DotAdapter(getContext());
        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            mList.add(i);
        }
        mAdapter.setData(mList);
        mLv.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }
}
