package com.yb.demo.activity.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.videolibrary.video.VideoViewLayout;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/4/17 20:53
 */
public class VideoActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, VideoActivity.class);
        context.startActivity(starter);
    }

    VideoViewLayout videoViewLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        videoViewLayout = (VideoViewLayout) findViewById(R.id.id_activity_video);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void play(View view) {
        videoViewLayout.setVideoURI(Uri.parse("http://koalareading-online.oss-cn-beijing.aliyuncs.com/multimedia/ER-teacher.mp4"));
        videoViewLayout.start();
    }
}
