package com.yb.demo.adapter.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yb on 2017/9/18.
 */
public class Md1PageAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;

    public Md1PageAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return getCount() > position ? mList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
}
