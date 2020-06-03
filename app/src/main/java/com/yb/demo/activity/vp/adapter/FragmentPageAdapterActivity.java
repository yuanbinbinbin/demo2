package com.yb.demo.activity.vp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentPageAdapterActivity extends BaseActivity {
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_adapter;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
//        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SampleFragment.newInstance("A"));
        fragments.add(SampleFragment.newInstance("B"));
        fragments.add(SampleFragment.newInstance("C"));
        fragments.add(SampleFragment.newInstance("D"));
        Adapter adapter = new Adapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private static class Adapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public Adapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
