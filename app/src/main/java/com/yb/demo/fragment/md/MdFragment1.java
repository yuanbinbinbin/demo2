package com.yb.demo.fragment.md;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yb.demo.R;
import com.yb.demo.adapter.recyclerviews.BottomSheetDialogRvAdapter;
import com.yb.demo.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */
public class MdFragment1 extends BaseFragment {
    RecyclerView mRv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_md1;
    }

    @Override
    protected void initView() {
        mRv = (RecyclerView) findViewById(R.id.fragment_md1_rv);
    }

    @Override
    protected void initData() {
        BottomSheetDialogRvAdapter adapter = new BottomSheetDialogRvAdapter(getActivity());
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            list.add("" + i);
        }
        adapter.setData(list);
        mRv.setHasFixedSize(false);
        mRv.setAdapter(adapter);
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
    }

    @Override
    protected void initListener() {
//        mRv.setNestedScrollingEnabled(false);
    }

    public static MdFragment1 newMdFragment1() {
        return new MdFragment1();
    }

    public void enableRecyclerView(){
        Log.e("test","enableRecyclerView");
        mRv.setNestedScrollingEnabled(true);
    }
    public void disableRecyclerView(){
        mRv.setNestedScrollingEnabled(false);
    }
}
