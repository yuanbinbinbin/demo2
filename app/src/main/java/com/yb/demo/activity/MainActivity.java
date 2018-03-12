package com.yb.demo.activity;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.demo.R;
import com.yb.demo.activity.animations.AnimationActivity;
import com.yb.demo.activity.customview.CustomViewActivity;
import com.yb.demo.activity.daemon.DaemonActivity;
import com.yb.demo.activity.dialog.DialogActivity;
import com.yb.demo.activity.eventbus.EventBusActivity;
import com.yb.demo.activity.exchange.ExchangeActivity;
import com.yb.demo.activity.file.FileSizeActivity;
import com.yb.demo.activity.image.ImageActivity;
import com.yb.demo.activity.jni.JniActivity;
import com.yb.demo.activity.listviews.ListViewActivity;
import com.yb.demo.activity.md.MDActivity;
import com.yb.demo.activity.memory.MemoryActivity;
import com.yb.demo.activity.multiprocess.aidl.AidlProcessActivity;
import com.yb.demo.activity.permission.PermissionActivity;
import com.yb.demo.activity.share.ShareActivity;
import com.yb.demo.activity.string.StringActivity;
import com.yb.demo.utils.ActivityUtil;
import com.yb.demo.utils.DeviceUtil;
import com.yb.demo.utils.LogUtil;
import com.yb.demo.weights.ResultListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static final String TAG = "MainActivity";
    private ResultListView mRLv;
    private TextView mTvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRLv = (ResultListView) findViewById(R.id.id_main_listview);
        mTvTitle = (TextView) findViewById(R.id.id_main_title);
    }

    @Override
    protected void initData() {
        List<Map<String, Object>> datas = getDatas();
        mRLv.setAdapter(new SimpleAdapter(this, datas, R.layout.item_main, new String[]{"text"}, new int[]{R.id.id_item_main_tv}));
    }

    @Override
    protected void initListener() {
        mRLv.setOnItemClickListener(this);
        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", mTvTitle.getWidth() + " : " + mTvTitle.getHeight() + "----- " + mTvTitle.getMeasuredWidth() + " : " + mTvTitle.getMeasuredHeight());
                mTvTitle.setText("66666666666666666666666666");
                Log.e("test", "title click");
            }
        });
        mTvTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("test", "title down");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("test", "title up");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("test", "title move");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.i(TAG, "position: " + position);
        switch (position) {
            case 1:
                ActivityUtil.startActivity(this, CustomViewActivity.class);
                break;
            case 2:
                ActivityUtil.startActivity(this, ListViewActivity.class);
                break;
            case 3:
                ActivityUtil.startActivity(this, ListViewActivity.class);
                break;
            case 8:
                ActivityUtil.startActivity(this, DialogActivity.class);
                break;
            case 9:
                ActivityUtil.startActivity(this, ImageActivity.class);
                break;
            case 10:
                ActivityUtil.startActivity(this, EventBusActivity.class);
                break;
            case 11:
                ActivityUtil.startActivity(this, MDActivity.class);
                break;
            case 12:
                ActivityUtil.startActivity(this, ChangeStatusColorActivity.class);
                break;
            case 13:
                ActivityUtil.startActivity(this, EmulatorCheckActivity.class);
                break;
            case 14:
                ActivityUtil.startActivity(this, FileSizeActivity.class);
                break;
            case 15:
//                ActivityUtil.startActivity(this, ProcessActivity.class);
                ActivityUtil.startActivity(this, AidlProcessActivity.class);
                break;
            case 16:
                customToast();
                break;
            case 17:
                ActivityUtil.startActivity(this, ShareActivity.class);
                break;
            case 18:
                ActivityUtil.startActivity(this, AnimationActivity.class);
                break;
            case 19:
                ActivityUtil.startActivity(this, DaemonActivity.class);
                break;
            case 20:
                ActivityUtil.startActivity(this, StringActivity.class);
                break;
            case 21:
                ActivityUtil.startActivity(this, ExchangeActivity.class);
                break;
            case 22:
                ActivityUtil.startActivity(this, MemoryActivity.class);
                break;
            case 23:
                ActivityUtil.startActivity(this, JniActivity.class);
                break;
            case 24:
                ActivityUtil.startActivity(this, PermissionActivity.class);
                break;
        }
    }

    public List<Map<String, Object>> getDatas() {
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("text", "View");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "ListView(3)");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "贝塞尔曲线");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "RecyclerView");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Animation");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "ViewPager");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "progress");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Dialog");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Image(4)");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "EventBus");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Material Design");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "StatusBarColor");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "硬件检测");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "FileSize");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "多进程");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "自定义Toast");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "伪装Share");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "动画");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "保活");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "SpannableString");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Activity动画切换");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Memory");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Jni");
        datas.add(data1);
        data1 = new HashMap<String, Object>();
        data1.put("text", "Permission");
        datas.add(data1);
        return datas;
    }


    private void customToast() {
        Toast result = new Toast(this);

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.custom_toast, null);
        View container = v.findViewById(R.id.id_custom_toast_container);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtil.getScreenWidth(this), DeviceUtil.dp2px(this, 60));
        container.setLayoutParams(lp);
        TextView tv = (TextView) v.findViewById(R.id.id_custom_toast_content);
        tv.setText("我是自定义Toast");

        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.TOP, 0, 0);

        result.show();
    }
}
