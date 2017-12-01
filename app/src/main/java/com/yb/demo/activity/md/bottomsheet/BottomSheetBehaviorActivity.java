package com.yb.demo.activity.md.bottomsheet;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.recyclerviews.BottomSheetDialogRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetBehaviorActivity extends BaseActivity {
    private View bg;
    private RecyclerView mRv;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_sheet_behavior;
    }

    @Override
    protected void initView() {
        mRv = (RecyclerView) findViewById(R.id.id_activity_bottom_sheet_behavior_rv);
        bg = findViewById(R.id.id_activity_bottom_sheet_behavior_container_bg);
    }

    @Override
    protected void initData() {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        BottomSheetDialogRvAdapter adapter = new BottomSheetDialogRvAdapter(this);
        List<String> mList = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            mList.add("test " + i);
        }
        adapter.setData(mList);
        mRv.setAdapter(adapter);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.id_activity_bottom_sheet_behavior_container));
        bottomSheetBehavior.setPeekHeight(0);
    }

    boolean isPersonCase = false;//是不是用户触发的点击了返回键或bottomsheetview 外部 为true

    @Override
    protected void initListener() {
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                Log.e("test", newState + " behavior " + isPersonCase);
                if (newState == BottomSheetBehavior.STATE_COLLAPSED && !isPersonCase) {
                    isShowBottomSheet = false;
                    hideBg();
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });
    }

    public void showSheetView(View view) {
        isShowBottomSheet = true;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        showBg();
        isPersonCase = false;
    }

    public void hideSheetView() {
        isPersonCase = true;
        hideBg();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        isShowBottomSheet = false;
    }

    private void showBg() {
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSheetView();
            }
        });
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        animation.setFillAfter(true);
        bg.startAnimation(animation);
    }

    private void hideBg() {
        Log.e("test", "hide bg");
        bg.setOnClickListener(null);
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        bg.startAnimation(animation);
    }

    private boolean isShowBottomSheet;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShowBottomSheet) {
                hideSheetView();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
