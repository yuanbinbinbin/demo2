package com.yb.demo.activity.vp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentStatePageAdapterActivity extends BaseActivity {
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_adapter;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
    }

    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SampleFragment.newInstance("State A"));
        fragments.add(SampleFragment.newInstance("State B"));
        fragments.add(SampleFragment.newInstance("State C"));
        fragments.add(SampleFragment.newInstance("State D"));
        Adapter adapter = new Adapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    private static class Adapter extends FragmentStatePagerAdapter {
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
