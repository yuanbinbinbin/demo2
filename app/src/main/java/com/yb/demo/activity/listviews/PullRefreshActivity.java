package com.yb.demo.activity.listviews;

import android.view.View;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.listviews.SimpleAdapter;
import com.yb.demo.utils.ToastUtil;
import com.yb.demo.weights.ResultListView;
import java.util.ArrayList;
import java.util.List;

public class PullRefreshActivity extends BaseActivity implements ResultListView.OnRefreshListener {
    private ResultListView mRLv;
    private SimpleAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull_refresh;
    }

    @Override
    protected void initView() {
        initCommonView();
        mRLv = (ResultListView) findViewById(R.id.id_pull_refresh_listview);
        mTvTopBarTitle.setText("下拉刷新上拉加载更多ListView");
        mIvTopBarBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        mAdapter = new SimpleAdapter(this, getDatas());
        mRLv.setAdapter(mAdapter, this, PullRefreshActivity.class);
    }

    @Override
    protected void initListener() {
        mRLv.setonRefreshListener(this);
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

    /**
     * 刷新回调
     */
    @Override
    public void onRefresh() {
        ToastUtil.showShortTime(this,"refresh");
    }

    /**
     * 加载更多回调
     */
    @Override
    public void onLoadMore() {
        ToastUtil.showShortTime(this,"loadMore");
    }

    /**
     * 手动刷新
     * @param view
     */
    public void refresh(View view){
        mRLv.refreshing();
      //  mRLv.refreshingTop();手动刷新被置顶
    }

    /**
     * 刷新完成
     * @param view
     */
    public void refreshComplete(View view){
        mAdapter.setData(getDatas());
        mRLv.onRefreshComplete();
    }

    /**
     * 加载中
     * @param view
     */
    public void loadMoreIng(View view){
        mRLv.setFooterView(ResultListView.FOOTER_SHOW);
    }

    /**
     * 加载完成
     * @param view
     */
    public void loadMoreComplete(View view){
        mAdapter.addData(getDatas());
        mRLv.setFooterView(ResultListView.FOOTER_NOT_SHOW);
    }

    /**
     * 移除底部
     * @param view
     */
    public void hideFooter(View view){
        mRLv.removeFooter();
    }

    /**
     * 添加底部
     * @param view
     */
    public void addFooter(View view){
        mRLv.addFooter();
    }

    /**
     * 添加可点击底部
     * @param view
     */
    public void footerCanClick(View view){
        mRLv.setFooterView(ResultListView.FOOTER_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortTime(PullRefreshActivity.this,"真的点我了呀!!!");
            }
        },"点我呀");
    }
}
