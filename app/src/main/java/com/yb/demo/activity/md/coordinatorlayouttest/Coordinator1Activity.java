package com.yb.demo.activity.md.coordinatorlayouttest;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.recyclerviews.BottomSheetDialogRvAdapter;
import com.yb.demo.adapter.viewpager.Md1PageAdapter;
import com.yb.demo.fragment.md.MdFragment1;
import com.yb.demo.utils.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

public class Coordinator1Activity extends BaseActivity {
    private ViewPager vp;
    Md1PageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator1;
    }

    @Override
    protected void initView() {
        vp = (ViewPager) findViewById(R.id.id_activity_coordinator1_vp);
    }

    @Override
    protected void initData() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(MdFragment1.newMdFragment1());
        list.add(MdFragment1.newMdFragment1());
        list.add(MdFragment1.newMdFragment1());
        adapter = new Md1PageAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(adapter);
        ViewGroup.LayoutParams pa = vp.getLayoutParams();
        pa.height = DeviceUtil.getScreenHeight(getContext()) - DeviceUtil.getStatusBarHeight(getContext()) - DeviceUtil.dp2px(getContext(), 55);
        vp.setLayoutParams(pa);
    }

    @Override
    protected void initListener() {
    }
}
