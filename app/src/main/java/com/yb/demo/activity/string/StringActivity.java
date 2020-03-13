package com.yb.demo.activity.string;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Shader;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.base.baselibrary.utils.SpannableStringUtil;

public class StringActivity extends BaseActivity {

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;
    private TextView mTv9;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_string;
    }

    @Override
    protected void initView() {
        mTv1 = (TextView) findViewById(R.id.id_activity_string_tv1);
        mTv2 = (TextView) findViewById(R.id.id_activity_string_tv2);
        mTv3 = (TextView) findViewById(R.id.id_activity_string_tv3);
        mTv4 = (TextView) findViewById(R.id.id_activity_string_tv4);
        mTv5 = (TextView) findViewById(R.id.id_activity_string_tv5);
        mTv6 = (TextView) findViewById(R.id.id_activity_string_tv6);
        mTv7 = (TextView) findViewById(R.id.id_activity_string_tv7);
        mTv8 = (TextView) findViewById(R.id.id_activity_string_tv8);
        mTv9 = (TextView) findViewById(R.id.id_activity_string_tv9);
    }

    @Override
    protected void initData() {
        initTv1();
        initTv2();
        initTv3();
        initTv4();
        initTv5();
        initTv6();
        initTv7();
        initTv8();
        initTv9();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTv3ValueAnimator.cancel();
        mTv8ValueAnimator.cancel();
        mTv9ValueAnimator.cancel();
    }

    private void initTv1() {
        String content = "￥9.8\n￥128.00";
        mTv1.setText(SpannableStringUtil.create(content)
                .setTextForegroundColor(Color.RED, 0, 4)
                .setTextForegroundColor(Color.DKGRAY, 5, 12)
                .setStrikeThrough(5, 12)
                .setTextSize(1.1f, 0, 4)
                .setTextSize(0.8f, 5, 12)
                .makeText());
    }

    private void initTv2() {
        String content = "32 + log28 = 12";
        mTv2.setText(SpannableStringUtil.create(content)
                .setSuperscript(1, 2)
                .setTextSize(0.6f, 1, 2)
                .setSubscript(8, 9)
                .setTextSize(0.6f, 8, 9)
                .setSuperscript(9, 10)
                .setTextSize(0.6f, 9, 10)
                .makeText());
    }

    //    -------------TV3  start--------------------
    private void initTv3() {
        String content = "小白兔，蹦蹦跳跳~真可爱！";
        mTv3.setText(content);
        mTv3ValueAnimator = ValueAnimator.ofInt(0, content.length());
        mTv3ValueAnimator.setInterpolator(new LinearInterpolator());
        mTv3ValueAnimator.setDuration(250 * content.length());
//        mTv3ValueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        mTv3ValueAnimator.setRepeatCount(3);
        mTv3ValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.e("test", "mtv3Runnable  执行中...");
                int position = (int) animation.getAnimatedValue();
                String s = mTv3.getText().toString();
                if (position >= s.length()) {
                    mTv3.setText(s);
                } else {
                    mTv3.setText(SpannableStringUtil.create(s)
                            .setTextSize(1.2f, position, position + 1)
                            .makeText());
                }
            }
        });
        mTv3ValueAnimator.start();
    }

    ValueAnimator mTv3ValueAnimator;
    //    -------------TV3  end--------------------


    private void initTv4() {

        mTv4.post(new Runnable() {
            @Override
            public void run() {
                String content =
                        "MIRROR以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙";
                mTv4.setText(SpannableStringUtil.create(content)
                        .setGradient(new int[]{Color.parseColor("#453a94"), Color.parseColor("#f43b47")}, Shader.TileMode.MIRROR, 0, content.length(), mTv4.getWidth() / 4)
                        .makeText());
            }
        });
    }

    private void initTv5() {

        mTv5.post(new Runnable() {
            @Override
            public void run() {
                String content =
                        "CLAMP边缘拉伸,凑数字凑数字凑数字凑数字凑数字凑数字";
                mTv5.setText(SpannableStringUtil.create(content)
                        .setGradient(new int[]{Color.parseColor("#453a94"), Color.parseColor("#f43b47")}, Shader.TileMode.CLAMP, 0, content.length(), mTv4.getWidth() / 4)
                        .makeText());
            }
        });
    }

    private void initTv6() {

        mTv6.post(new Runnable() {
            @Override
            public void run() {
                String content =
                        "REPEAT在水平和垂直两个方向上重复，相邻图像没有间隙";
                mTv6.setText(SpannableStringUtil.create(content)
                        .setGradient(new int[]{Color.parseColor("#453a94"), Color.parseColor("#f43b47")}, Shader.TileMode.REPEAT, 0, content.length(), mTv4.getWidth() / 4)
                        .makeText());
            }
        });
    }

    private void initTv7() {
        mTv7.post(new Runnable() {
            @Override
            public void run() {
                String content =
                        "编程使我快乐，我爱编程，我爱写代码!编程使我快乐，我爱编程，我爱写代码!编程使我快乐，我爱编程，我爱写代码!";
                mTv7.setText(SpannableStringUtil.create(content)
                        .setGradient(new int[]{Color.parseColor("#453a94"), Color.parseColor("#f43b47")}, Shader.TileMode.REPEAT, 0, content.length(), mTv4.getWidth())
                        .makeText());
            }
        });
    }

    //    -------------TV8  start--------------------
    private void initTv8() {
        mTv8.post(new Runnable() {
            @Override
            public void run() {
                String content =
                        "编程使我快乐，我爱编程，我爱写代码!编程使我快乐，我爱编程，我爱写代码!编程使我快乐，我爱编程，我爱写代码!";
                final SpannableStringUtil.GradientSpan gradientSpan = new SpannableStringUtil.GradientSpan(new int[]{Color.parseColor("#453a94"), Color.parseColor("#f43b47")}, mTv8.getWidth(), Shader.TileMode.MIRROR);

                final SpannableStringUtil spannableString = SpannableStringUtil.create(content)
                        .setGradient(gradientSpan, 0, content.length());
                mTv8.setText(spannableString.makeText());

//                Color.parseColor("#2af598"), Color.parseColor("#009efd"),
                mTv8ValueAnimator = ValueAnimator.ofFloat(0, 2);
                mTv8ValueAnimator.setInterpolator(new LinearInterpolator());
                mTv8ValueAnimator.setDuration(5000);
                mTv8ValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mTv8ValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        gradientSpan.updateGradientPosition((Float) animation.getAnimatedValue());
                        mTv8.setText(spannableString.makeText());
                    }
                });
                mTv8ValueAnimator.start();

            }
        });

    }

    ValueAnimator mTv8ValueAnimator;

    //    -------------TV8  end--------------------

    private void initTv9() {
        String content =
                "编程使我快乐，我爱编程，我爱写代码!编程使我快乐，我爱编程，我爱写代码!编程使我快乐，我爱编程，我爱写代码!";
        mTv9.setText(content);
        mTv9.setTextColor(Color.parseColor("#fbc2eb"));
        int startColor = Color.parseColor("#fbc2eb");
        final int startRed = Color.red(startColor);
        final int startBlue = Color.blue(startColor);
        final int startGreen = Color.green(startColor);
        int endColor = Color.parseColor("#a6c1ee");
        final int diffRed = Color.red(endColor) - startRed;//差值RED
        final int diffBlue = Color.blue(endColor) - startBlue;//差值BLUE
        final int diffGreen = Color.green(endColor) - startGreen;//差值GREEN
        mTv9ValueAnimator = ValueAnimator.ofFloat(0, 1, 0);
        mTv9ValueAnimator.setDuration(10000);
        mTv9ValueAnimator.setInterpolator(new LinearInterpolator());
        mTv9ValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mTv9ValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percnt = (float) animation.getAnimatedValue();
                int r = (int) (startRed + percnt * diffRed);
                int g = (int) (startGreen + percnt * diffGreen);
                int b = (int) (startBlue + percnt * diffBlue);
                mTv9.setTextColor(Color.rgb(r, g, b));
            }
        });
        mTv9ValueAnimator.start();
    }

    ValueAnimator mTv9ValueAnimator;
}
