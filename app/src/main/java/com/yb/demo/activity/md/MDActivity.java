package com.yb.demo.activity.md;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.activity.md.bottomsheet.BottomSheetBehaviorActivity;
import com.yb.demo.activity.md.coordinatorlayouttest.Coordinator1Activity;
import com.yb.demo.activity.md.custombehavior.CustomBehavior1Activity;
import com.yb.demo.activity.md.sina.LikeSinaActivity;
import com.yb.demo.adapter.recyclerviews.BottomSheetDialogRvAdapter;
import com.yb.demo.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class MDActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_md;
    }

    @Override
    protected void initView() {
        initCommonView();
    }

    @Override
    protected void initData() {
        mTvTopBarTitle.setText("Material Design");
    }

    @Override
    protected void initListener() {

    }

    public void onSituation1(View v) {
        ActivityUtil.startActivity(getContext(), CustomBehavior1Activity.class);
    }

    //BottomSheetDialog的显示
    private BottomSheetDialog bottomSheetDialog;

    public void onSituation2(View v) {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null);
            RecyclerView rv = (RecyclerView) view.findViewById(R.id.id_bottom_sheet_rv);
            rv.setLayoutManager(new LinearLayoutManager(this));
            BottomSheetDialogRvAdapter adapter = new BottomSheetDialogRvAdapter(this);
            List<String> mList = new ArrayList<String>();
            for (int i = 0; i < 30; i++) {
                mList.add("test " + i);
            }
            adapter.setData(mList);
            rv.setAdapter(adapter);
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
            View vv = bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
            final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(vv);
//            bottomSheetBehavior.setPeekHeight(0);//设置折叠时的高度
//            bottomSheetBehavior.setSkipCollapsed(true);//是否跳过半折叠状态，当Dialog处于全打开状态时，下滑，如果设置为true，则直接跳过半打开状态直接到隐藏状态，如果设置为false，则跳到半打开状态，在下滑才跳到打开状态

            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetDialog.dismiss();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//STATE_COLLAPSED半打开状态，STATE_EXPANDED为全打开
                    }
                }

                @Override
                public void onSlide(View bottomSheet, float slideOffset) {
                    Log.e("test", "slideOffset:" + slideOffset);
                }

            });
        }

        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        } else {
            bottomSheetDialog.show();
        }
    }

    public void onSituation3(View v) {
        ActivityUtil.startActivity(getContext(), BottomSheetBehaviorActivity.class);
    }

    public void onSituation4(View v) {
        ActivityUtil.startActivity(getContext(), Coordinator1Activity.class);
    }

    public void onSituation5(View v) {
        ActivityUtil.startActivity(getContext(), LikeSinaActivity.class);
    }
}
