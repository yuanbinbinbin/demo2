package com.yb.demo.activity.vp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.activity.vp.animation.AccordionTransformer;
import com.yb.demo.activity.vp.animation.BackgroundToForegroundTransformer;
import com.yb.demo.activity.vp.animation.CubeInTransformer;
import com.yb.demo.activity.vp.animation.CubeOutTransformer;
import com.yb.demo.activity.vp.animation.DefaultTransformer;
import com.yb.demo.activity.vp.animation.DepthPageTransformer;
import com.yb.demo.activity.vp.animation.FlipHorizontalTransformer;
import com.yb.demo.activity.vp.animation.FlipVerticalTransformer;
import com.yb.demo.activity.vp.animation.ForegroundToBackgroundTransformer;
import com.yb.demo.activity.vp.animation.RotateDownTransformer;
import com.yb.demo.activity.vp.animation.RotateUpTransformer;
import com.yb.demo.activity.vp.animation.ScaleInOutTransformer;
import com.yb.demo.activity.vp.animation.StackTransformer;
import com.yb.demo.activity.vp.animation.TabletTransformer;
import com.yb.demo.activity.vp.animation.ZoomInTransformer;
import com.yb.demo.activity.vp.animation.ZoomOutSlideTransformer;
import com.yb.demo.activity.vp.animation.ZoomOutTranformer;
import com.yb.demo.dialog.DialogManager;
import com.yb.demo.entity.dialog.ListItem;

import java.util.ArrayList;
import java.util.List;

//viewPager翻页动画
public class ViewPagerAnimationActivity extends BaseActivity {


    public static void start(Context context) {
        Intent starter = new Intent(context, ViewPagerAnimationActivity.class);
        context.startActivity(starter);
    }


    private TextView mTvAnimationTitle;
    private View view1, view2, view3;
    private ViewPager viewPager;

    private List<View> viewList;
    private List<String> titleList;

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_vp_animation;
    }

    @Override
    protected void initView() {
        mTvAnimationTitle = (TextView) findViewById(R.id.id_activity_vp_animation_title);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        LayoutInflater lf = getLayoutInflater().from(this);
        view1 = lf.inflate(R.layout.item_vp_animation_layout1, null);
        view2 = lf.inflate(R.layout.item_vp_animation_layout2, null);
        view3 = lf.inflate(R.layout.item_vp_animation_layout3, null);

        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        titleList = new ArrayList<String>();
        titleList.add("1");
        titleList.add("2");
        titleList.add("3");

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(viewList.get(position));

            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titleList.get(position);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

        };

        viewPager.setAdapter(pagerAdapter);

        changeAnimation(4);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


    DialogManager dialogManager;

    public void changAnimation(View view) {
        if (dialogManager == null) {
            dialogManager = new DialogManager(getContext());
        }
        final List<ListItem> list = new ArrayList<ListItem>();
        list.add(new ListItem("默认0", "0"));
        list.add(new ListItem("左右黏合滑动1", "1"));
        list.add(new ListItem("进入从小变大2", "2"));
        list.add(new ListItem("3D向前飞出屏幕3", "3"));
        list.add(new ListItem("3D箱子旋转4", "4"));
        list.add(new ListItem("层式进入5", "5"));
        list.add(new ListItem("中心竖轴旋转6", "6"));
        list.add(new ListItem("中心横轴旋转7", "7"));
        list.add(new ListItem("退出从大到小8", "8"));
        list.add(new ListItem("扇形动画bottom 9", "9"));
        list.add(new ListItem("扇形动画up 10", "10"));
        list.add(new ListItem("退出大->小，进入小->大11", "11"));
        list.add(new ListItem("遮盖翻页12", "12"));
        list.add(new ListItem("内旋3D翻页13", "13"));
        list.add(new ListItem("不翻页中心缩小14", "14"));
        list.add(new ListItem("左右半透明平移15", "15"));
        list.add(new ListItem("透明度放大变换16", "16"));

        //region 点击事件
        dialogManager.showDialog(DialogManager.DialogStyle.BOTTOM_SELECT_NO_TITLE, new DialogManager.OnClickListenerContent() {
            @Override
            public void onClick(View view, Object... content) {
                switch (view.getId()) {
                    case R.id.dialog_list_lv:
                        if (content != null && content.length > 1) {
                            int position = 0;
                            if (content[0] != null && content[0] instanceof Integer) {
                                position = (int) content[0];
                                changeAnimation(position);
                                mTvAnimationTitle.setText(list.get(position).getContent());
                            }
                            Toast.makeText(getContext(), "点击了" + position + " 内容： " + content[1], Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.dialog_list_tv_cancel:
                        Toast.makeText(getContext(), "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (dialogManager != null) {
                    dialogManager.dismissDialog();
                }
            }
        }, list);
        //endregion
    }

    private void changeAnimation(int position) {
        switch (position) {
            case 0:
                //默认
                viewPager.setPageTransformer(true, new DefaultTransformer());
                break;
            case 1:
                viewPager.setPageTransformer(true, new AccordionTransformer());
                break;
            case 2:
                viewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
                break;
            case 3:
                viewPager.setPageTransformer(true, new CubeInTransformer());
                break;
            case 4:
                viewPager.setPageTransformer(true, new CubeOutTransformer());
                break;
            case 5:
                viewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case 6:
                viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
                break;
            case 7:
                viewPager.setPageTransformer(true, new FlipVerticalTransformer());
                break;
            case 8:
                viewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());
                break;
            case 9:
                viewPager.setPageTransformer(true, new RotateDownTransformer());
                break;
            case 10:
                viewPager.setPageTransformer(true, new RotateUpTransformer());
                break;
            case 11:
                viewPager.setPageTransformer(true, new ScaleInOutTransformer());
                break;
            case 12:
                viewPager.setPageTransformer(true, new StackTransformer());
                break;
            case 13:
                viewPager.setPageTransformer(true, new TabletTransformer());
                break;
            case 14:
                viewPager.setPageTransformer(true, new ZoomInTransformer());
                break;
            case 15:
                viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                break;
            case 16:
                viewPager.setPageTransformer(true, new ZoomOutTranformer());
                break;
            default:
                break;
        }
    }
}
