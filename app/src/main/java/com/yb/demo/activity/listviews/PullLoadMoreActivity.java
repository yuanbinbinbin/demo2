package com.yb.demo.activity.listviews;

import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.listviews.SimpleAdapter;
import com.yb.demo.utils.ToastUtil;
import com.yb.demo.weights.PullLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

public class PullLoadMoreActivity extends BaseActivity implements PullLoadMoreListView.GetMoreDataListener {
    PullLoadMoreListView mPullLoadMoreLv;
    private SimpleAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull_load_more;
    }

    @Override
    protected void initView() {
        initCommonView();
        mPullLoadMoreLv = (PullLoadMoreListView) findViewById(R.id.id_pull_load_more_listview);

        mTvTopBarTitle.setText("下拉加载更多ListView");
        mIvTopBarBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        mAdapter = new SimpleAdapter(this, getDatas());
        mPullLoadMoreLv.setAdapter(mAdapter, this);
    }

    @Override
    protected void initListener() {
        mPullLoadMoreLv.setmGetMoreDataListener(this);
        mIvTopBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public List<String> getDatas() {
        List<String> datas = new ArrayList<String>();
        for(int i = 0; i <10;i++){
            datas.add(""+i);
        }
        return datas;
    }

    @Override
    public void getMore() {
        ToastUtil.showShortTime(this,"获取更多条数据");
    }

    public void loadMoreIng(View view){
        mPullLoadMoreLv.refreshing();
    }
    public void loadMoreComplete(View view){
        List<String> list = getDatas();
        mAdapter.insertDatasAtPosition(list,0);
        mPullLoadMoreLv.setSelection(list.size());
        mPullLoadMoreLv.onRefreshComplete();
    }
}
