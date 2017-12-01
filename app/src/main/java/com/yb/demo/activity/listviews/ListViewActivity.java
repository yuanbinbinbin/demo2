package com.yb.demo.activity.listviews;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.ActivityUtil;
import com.yb.demo.utils.ToastUtil;
import com.yb.demo.weights.ResultListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ResultListView mRLv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_view;
    }

    @Override
    protected void initView() {
        initCommonView();
        mRLv = (ResultListView) findViewById(R.id.id_list_view_listview);
        mTvTopBarTitle.setText("ListView");
        mIvTopBarBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        List<Map<String, Object>> datas = getDatas();
        mRLv.setAdapter(new SimpleAdapter(this, datas, R.layout.item_main, new String[]{"text"}, new int[]{R.id.id_item_main_tv}));
    }

    @Override
    protected void initListener() {
        mRLv.setOnItemClickListener(this);
        mIvTopBarBack.setOnClickListener(this);
    }

    public List<Map<String, Object>> getDatas() {
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("text", "正常ListView");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "下拉刷新，上拉加载更多");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "下拉加载更多");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "更新单个item");
        datas.add(data1);
        return datas;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                ToastUtil.showShortTime(this, "这就是一个正常的ListView");
                break;
            case 2:
                ActivityUtil.startActivity(this, PullRefreshActivity.class);
                break;
            case 3:
                ActivityUtil.startActivity(this, PullLoadMoreActivity.class);
                break;
            case 4:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_common_title_bar_back:
                finish();
                return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("test","onCreate");
        if(savedInstanceState != null){
            Log.e("test","save onCreate"+savedInstanceState.get("123"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("123","6666666666");
        Log.e("test", "onSaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("test", "onRestoreInstanceState");
        if(savedInstanceState != null){
            Log.e("test","save onRestoreInstanceState"+savedInstanceState.get("123"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("test","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("test","onStop");
    }
}
