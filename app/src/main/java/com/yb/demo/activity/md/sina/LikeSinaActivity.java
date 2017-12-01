package com.yb.demo.activity.md.sina;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

public class LikeSinaActivity extends BaseActivity {
    AppBarLayout appBarLayout;
    View headerView;
    NestedScrollView nestedScrollView;
    int headerHeight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_like_sina;
    }

    @Override
    protected void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.id_activity_like_sina_appbar);
        headerView = findViewById(R.id.id_activity_like_sina_header);
        nestedScrollView = (NestedScrollView) findViewById(R.id.id_activity_like_sina_nestedScrollView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getHeight()) {
                    headerHeight = headerView.getHeight();
                    AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerView.getLayoutParams();
                    mParams.setScrollFlags(0);
                    headerView.setLayoutParams(mParams);
                    headerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监听返回键
            if (headerView.getVisibility() == View.GONE) {
                /*微博的效果是，点击返回键拉出上面隐藏的view，并同时让list滚动到最顶部， 我这里只给第一个fragment的RecyclerView增加了跳到第0个位置的操作，这里大家可以自行去编写逻辑 */

                headerView.setVisibility(View.VISIBLE);
                AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerView.getLayoutParams();
                mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                headerView.setLayoutParams(mParams);
                Animator animator = ObjectAnimator.ofFloat(appBarLayout, "translationY", -headerView.getHeight(), 0);
                animator.setDuration(300).start();

                nestedScrollView.smoothScrollTo(0, 0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}