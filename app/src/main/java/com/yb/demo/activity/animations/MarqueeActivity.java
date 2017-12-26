package com.yb.demo.activity.animations;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.adapter.marquee.TestMarqueeViewHelper;
import com.yb.demo.entity.animations.marquee.MarqueeViewTestBean;
import com.yb.demo.weights.marquee.AutoTextSwitcher;
import com.yb.demo.weights.marquee.MarqueeView;

import java.util.ArrayList;
import java.util.List;

public class MarqueeActivity extends BaseActivity {
    private int time = 0;

    private TextSwitcher mTextSwitch1;
    private AutoTextSwitcher mTextSwitch2;
    private ImageSwitcher mImageSwitch1;
    private MarqueeView mMarqueeView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_marquee;
    }

    @Override
    protected void initView() {
        initTextSwitch();
        initImageSwitch();
        initMarqueeView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private void initTextSwitch() {
        mTextSwitch1 = (TextSwitcher) findViewById(R.id.id_activity_marquee_text_switcher1);
        mTextSwitch2 = (AutoTextSwitcher) findViewById(R.id.id_activity_marquee_text_switcher2);


        //设置view创建工厂
        mTextSwitch1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //并不会无限创建  log只打印两次
                Log.e("test", "mTextSwitch1  make view");
                TextView tv = new TextView(getContext());
                tv.setTextSize(30);
                tv.setTextColor(Color.RED);
                return tv;
            }
        });

        mTextSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time++;
                mTextSwitch1.setText("" + time);
            }
        });
        mTextSwitch1.setText("" + time);

        List<String> texts = new ArrayList<String>();
        texts.add("第1条消息");
        texts.add("第2条消息");
        texts.add("第3条消息");
        mTextSwitch2.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(getContext());
                tv.setTextSize(30);
                tv.setTextColor(Color.RED);
                return tv;
            }
        });
        mTextSwitch2.setTexts(texts);
    }

    private int imgs[] = new int[]{R.drawable.live_ic_sandwich, R.drawable.live_ic_kele, R.drawable.live_ic_good, R.drawable.live_ic_glasses,
            R.drawable.live_ic_love1, R.drawable.live_ic_love2};
    private int imgPosition = 0;

    private void initImageSwitch() {
        mImageSwitch1 = (ImageSwitcher) findViewById(R.id.id_activity_marquee_image_switcher1);

        mImageSwitch1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView iv = new ImageView(getContext());
                return iv;
            }
        });

        mImageSwitch1.setImageResource(imgs[imgPosition]);

        mImageSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPosition++;
                imgPosition = imgPosition % imgs.length;
                mImageSwitch1.setImageResource(imgs[imgPosition]);
            }
        });
    }

    private void initMarqueeView(){
        mMarqueeView = (MarqueeView) findViewById(R.id.id_activity_marquee_custom_view);
        TestMarqueeViewHelper helper = new TestMarqueeViewHelper();
        List<MarqueeViewTestBean> list = new ArrayList<MarqueeViewTestBean>();
        list.add(new MarqueeViewTestBean(R.drawable.live_ic_donuts,"我是第1条测试数据"));
//        list.add(new MarqueeViewTestBean(R.drawable.live_ic_glasses,"我是第2条测试数据我是第2条测试数据我是第2条测试数据"));
//        list.add(new MarqueeViewTestBean(R.drawable.live_ic_good,"我是第3条测试数据我是第3条测试数据"));
//        list.add(new MarqueeViewTestBean(R.drawable.live_ic_kele,"我是第4条测试数据"));
//        list.add(new MarqueeViewTestBean(R.drawable.live_ic_love1,"我是第5条测试数据我是第5条测试数据我是第5条测试数据我是第5条测试数据"));
        helper.setmList(list);
        mMarqueeView.setAdapter(helper);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mTextSwitch2.setIsShowing(false);
        mMarqueeView.setIsShowing(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextSwitch2.setIsShowing(true);
        mMarqueeView.setIsShowing(true);
    }

}
