package com.yb.demo.activity.animations;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.base.baselibrary.interfaces.listeners.OnItemClickListener;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.listviews.animations.ParabolaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ParabolaActivity extends BaseActivity {
    private View mView2;
    private View mViewBezier;
    private View mViewEnd;
    private ListView mLv;

    private ParabolaAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_parabola;
    }

    @Override
    protected void initView() {
        mView2 = findViewById(R.id.id_activity_parabola_2);
        mViewBezier = findViewById(R.id.id_activity_parabola_bezier);
        mViewEnd = findViewById(R.id.id_activity_parabola_end);
        mLv = (ListView) findViewById(R.id.id_activity_parabola_lv);
    }

    @Override
    protected void initData() {
        mAdapter = new ParabolaAdapter(getActivity());
        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            mList.add(i);
        }
        mAdapter.setData(mList);
        mLv.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void parabola2(final View v) {
        final PointF startPointF = new PointF(v.getX(), v.getY());
        PointF endPointF = new PointF(mViewEnd.getX(), mViewEnd.getY());

        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setObjectValues(startPointF, endPointF);
        animator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

                return new PointF(startValue.x + fraction * (endValue.x - startValue.x), startValue.y + fraction * fraction * (endValue.y - startValue.y));
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                v.setX(point.x);
                v.setY(point.y);
                v.setAlpha(1 - animation.getAnimatedFraction());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setX(startPointF.x);
                v.setY(startPointF.y);
                v.setAlpha(1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void bezier(final View v) {
        final PointF startPointF = new PointF(v.getX(), v.getY());
        PointF endPointF = new PointF(mViewEnd.getX(), mViewEnd.getY());

        Path path = new Path();
        path.moveTo(startPointF.x, startPointF.y);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可//第二个是参考坐标的y值，位置越高，越往上走
        path.quadTo((startPointF.x + endPointF.x) / 2, startPointF.y / 2, endPointF.x, endPointF.y);

        final PathMeasure pathMeasure = new PathMeasure(path, false);

        ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setObjectValues(startPointF, endPointF);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] pos = new float[2];
                pathMeasure.getPosTan(value, pos, null);
                v.setX(pos[0]);
                v.setY(pos[1]);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setX(startPointF.x);
                v.setY(startPointF.y);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position, Object... objects) {
            //初始化起点和终点
            final ViewGroup parent = (ViewGroup) mViewEnd.getParent();
            int[] parentLoaction = new int[2];
            parent.getLocationOnScreen(parentLoaction);
            int[] viewLoaction = new int[2];
            v.getLocationOnScreen(viewLoaction);
            int startX = viewLoaction[0] - parentLoaction[0];
            int startY = viewLoaction[1] - parentLoaction[1];
            int[] endLoaction = new int[2];
            mViewEnd.getLocationOnScreen(endLoaction);
            float endX = mViewEnd.getX();
            float endY = mViewEnd.getY();
            //初始化路径
            Path path = new Path();
            path.moveTo(startX, startY);
            path.quadTo((startX + endX) / 2, startY/2, endX, endY);
            final PathMeasure pathMeasure = new PathMeasure(path, false);
            //初始化Viwe
            final ImageView animatorView = new ImageView(getContext());
            animatorView.setImageResource(R.color.color_0072ff);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(40, 40);
            animatorView.setLayoutParams(params);
            animatorView.setX(startX);
            animatorView.setY(startY);
            parent.addView(animatorView);
            //初始化Animator
            ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
            animator.setDuration(1000);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float[] pos = new float[2];
                    float distance = (float) animation.getAnimatedValue();
                    pathMeasure.getPosTan(distance, pos, null);
                    animatorView.setX(pos[0]);
                    animatorView.setY(pos[1]);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    parent.removeView(animatorView);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    };
}
