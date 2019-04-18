package com.yb.videolibrary.video;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yb.videolibrary.R;
import com.yb.videolibrary.video.inter.IVideoPlayControl;
import com.yb.videolibrary.video.inter.IVideoPlayListener;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/4/16 19:53
 */
public class VideoViewLayout extends FrameLayout implements IVideoPlayControl, View.OnClickListener {
    private Context mContext;
    private IVideoPlayControl mVideoView;
    private IVideoPlayListener outPlayListener;
    private ImageView mIvPlay;

    //region right
    private View mViewRightContainer;

    //endregion

    //region left
    private View mViewLeftContainer;

    //endregion

    //region top
    private View mViewTopContainer;
    private View mViewTopBack;
    private ImageView mIvTopDanmu;
    private ImageView mIvTopRefresh;
    private ImageView mIvTopShare;
    private ImageView mIvTopSetting;
    private TextView mTvTopTitle;
    //endregion

    //region bottom
    private View mViewBottomContainer;
    private TextView mTvBottomPlayTime;
    private TextView mTvBottomTotalTime;
    private ImageView mIvBottomFullScreen;
    private SeekBar mSbBottomProgress;
    //endregion

    //region net error
    private View mViewNetErrorContainer;
    private View mViewNetErrorBack;
    private View mViewNetErrorPlay;
    private TextView mTvNetErrorInfo;

    //endregion

    //region loading
    View mViewLoadingContainer;
    ImageView mIvLoading;
    Animation loadingAnimation;
    //endregion

    public VideoViewLayout(@NonNull Context context) {
        this(context, null);
    }

    public VideoViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
        initListener();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_videolayout, this);
        mVideoView = (IVideoPlayControl) findViewById(R.id.id_view_videolayout_video);
        mVideoView.registerPlayListener(innerPlayListener);
        mIvPlay = (ImageView) findViewById(R.id.id_view_videolayout_play);
        mIvPlay.setOnClickListener(this);
        //region right
        mViewRightContainer = findViewById(R.id.id_view_videolayout_right_container);

        //endregion

        //region left
        mViewLeftContainer = findViewById(R.id.id_view_videolayout_left_container);

        //endregion

        //region top
        mViewTopContainer = findViewById(R.id.id_view_videolayout_top_container);
        mViewTopBack = findViewById(R.id.id_view_videolayout_top_back);
        mViewTopBack.setOnClickListener(this);
        mIvTopDanmu = (ImageView) findViewById(R.id.id_view_videolayout_bottom_danmu);
        mIvTopDanmu.setOnClickListener(this);
        mIvTopRefresh = (ImageView) findViewById(R.id.id_view_videolayout_bottom_refresh);
        mIvTopRefresh.setOnClickListener(this);
        mIvTopShare = (ImageView) findViewById(R.id.id_view_videolayout_bottom_share);
        mIvTopShare.setOnClickListener(this);
        mIvTopSetting = (ImageView) findViewById(R.id.id_view_videolayout_top_setting);
        mIvTopSetting.setOnClickListener(this);
        mTvTopTitle = (TextView) findViewById(R.id.id_view_videolayout_top_title);
        //endregion

        //region bottom
        mViewBottomContainer = findViewById(R.id.id_view_videolayout_bottom_container);
        mTvBottomPlayTime = (TextView) findViewById(R.id.id_view_videolayout_bottom_paly_time);
        mTvBottomTotalTime = (TextView) findViewById(R.id.id_view_videolayout_bottom_total_time);
        mIvBottomFullScreen = (ImageView) findViewById(R.id.id_view_videolayout_bottom_full_screen);
        mIvBottomFullScreen.setOnClickListener(this);
        mSbBottomProgress = (SeekBar) findViewById(R.id.id_view_videolayout_bottom_seekbar);
        mSbBottomProgress.setMax(100);
        //endregion

        //region loading
        mViewLoadingContainer = findViewById(R.id.id_view_videolayout_loading_container);
        mIvLoading = (ImageView) findViewById(R.id.id_view_videolayout_loading_iv);
        loadingAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_video_loading);
        //endregion

        //region net error
        mViewNetErrorContainer = findViewById(R.id.id_view_videolayout_net_error_container);
        mViewNetErrorContainer.setOnClickListener(null);

        mViewNetErrorBack = findViewById(R.id.id_view_videolayout_net_error_back);
        mViewNetErrorBack.setOnClickListener(this);
        mViewNetErrorPlay = findViewById(R.id.id_view_videolayout_net_error_play);
        mViewNetErrorPlay.setOnClickListener(this);
        mTvNetErrorInfo = (TextView) findViewById(R.id.id_view_videolayout_net_error_info);
        //endregion

    }

    private void initData() {

    }

    private void initListener() {
    }

    @Override
    public void setVideoPath(String path) {
        if (mVideoView != null) {
            mVideoView.setVideoPath(path);
        }
    }

    @Override
    public void setVideoURI(Uri uri) {
        if (mVideoView != null) {
            mVideoView.setVideoURI(uri);
        }
    }

    @Override
    public void playOrPause() {
        if (mVideoView != null) {
            mVideoView.playOrPause();
        }
    }

    @Override
    public void start() {
        if (mVideoView != null) {
            mVideoView.start();
        }
    }

    @Override
    public void stop() {
        if (mVideoView != null) {
            mVideoView.stop();
        }
    }

    @Override
    public void pause() {
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    public int getDuration() {
        if (mVideoView != null) {
            return mVideoView.getDuration();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (mVideoView != null) {
            return mVideoView.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        if (mVideoView != null) {
            mVideoView.seekTo(pos);
        }
    }

    @Override
    public boolean isPlaying() {
        if (mVideoView != null) {
            return mVideoView.isPlaying();
        }
        return false;
    }

    @Override
    public int getBufferPercentage() {
        if (mVideoView != null) {
            return mVideoView.getBufferPercentage();
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        if (mVideoView != null) {
            return mVideoView.canPause();
        }
        return false;
    }

    @Override
    public boolean canSeekTo() {
        if (mVideoView != null) {
            return mVideoView.canSeekTo();
        }
        return false;
    }

    @Override
    public int getVideoWidth() {
        if (mVideoView != null) {
            return mVideoView.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (mVideoView != null) {
            return mVideoView.getVideoHeight();
        }
        return 0;
    }

    @Override
    public void resetHolderSize(int suggestWidth, int suggestHeight) {
        if (mVideoView != null) {
            mVideoView.resetHolderSize(suggestWidth, suggestHeight);
        }
    }

    @Override
    public void registerPlayListener(IVideoPlayListener listener) {
        outPlayListener = listener;
    }

    @Override
    public void activityResume() {
        if (mVideoView != null) {
            mVideoView.activityResume();
        }
    }

    @Override
    public void activityPause() {
        if (mVideoView != null) {
            mVideoView.activityPause();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.id_view_videolayout_play == id) {
            //播放或者暂停
        } else if (R.id.id_view_videolayout_net_error_back == id || R.id.id_view_videolayout_top_back == id) {
            //网络加载失败时的返回 || 正常播放时Top 返回按钮
        } else if (R.id.id_view_videolayout_net_error_play == id) {
            //网络加载失败时的播放
        } else if (R.id.id_view_videolayout_bottom_danmu == id) {
            //Top 显示或隐藏弹幕
        } else if (R.id.id_view_videolayout_bottom_refresh == id) {
            //Top 刷新
        } else if (R.id.id_view_videolayout_bottom_share == id) {
            //Top 分享
        } else if (R.id.id_view_videolayout_top_setting == id) {
            //Top 设置
        } else if (R.id.id_view_videolayout_bottom_full_screen == id) {

        }
    }


    private IVideoPlayListener innerPlayListener = new IVideoPlayListener() {

        @Override
        public void onPreparing() {
            if (outPlayListener != null) {
                outPlayListener.onPreparing();
            }
            showLoading();
        }

        @Override
        public void onPrepared() {
            if (outPlayListener != null) {
                outPlayListener.onPrepared();
            }
            hideLoading();
        }

        @Override
        public void onBufferingUpdate(int percent) {
            if (outPlayListener != null) {
                outPlayListener.onBufferingUpdate(percent);
            }
            mSbBottomProgress.setSecondaryProgress(percent);
        }

        @Override
        public void onBlockStart() {
            if (outPlayListener != null) {
                outPlayListener.onBlockStart();
            }
            showLoading();
        }

        @Override
        public void onBlockEnd() {
            if (outPlayListener != null) {
                outPlayListener.onBlockEnd();
            }
            hideLoading();
        }

        @Override
        public void onSeekStart() {
            if (outPlayListener != null) {
                outPlayListener.onSeekStart();
            }
            showLoading();
        }

        @Override
        public void onSeekEnd() {
            if (outPlayListener != null) {
                outPlayListener.onSeekEnd();
            }
            hideLoading();
        }

        @Override
        public void onPlay() {
            if (outPlayListener != null) {
                outPlayListener.onPlay();
            }
            hideLoading();
        }

        @Override
        public void onPlayProgress(int progress, int maxProgress, int bufferProgress) {
            if (outPlayListener != null) {
                outPlayListener.onPlayProgress(progress, maxProgress, bufferProgress);
            }
            Log.e("test123", "progress: " + progress + " maxProgress: " + maxProgress + " bufferProgress: " + bufferProgress);
            mTvBottomPlayTime.setText(covertTime(progress));
            mTvBottomTotalTime.setText(covertTime(maxProgress));
            mSbBottomProgress.setProgress((int) (progress * 100f / maxProgress));
        }

        @Override
        public void onPlayCompletion() {
            if (outPlayListener != null) {
                outPlayListener.onPlayCompletion();
            }
        }

        @Override
        public void onPause() {
            if (outPlayListener != null) {
                outPlayListener.onPause();
            }
        }

        @Override
        public void onError(int code, String msg) {
            if (outPlayListener != null) {
                outPlayListener.onError(code, msg);
            }
        }

        @Override
        public void onVideoSizeChange(int width, int height) {
            if (outPlayListener != null) {
                outPlayListener.onVideoSizeChange(width, height);
            }
        }
    };

    private String covertTime(int time) {
        time = time / 1000;
        int s = time % 60;
        int m = time / 60 % 60;
        int h = time / 60 / 60;
        StringBuilder sb = new StringBuilder();
        if (h > 9) {
            sb.append(h);
        } else {
            sb.append(0);
            sb.append(h);
        }
        sb.append(':');
        if (m > 9) {
            sb.append(m);
        } else {
            sb.append(0);
            sb.append(m);
        }
        sb.append(':');
        if (s > 9) {
            sb.append(s);
        } else {
            sb.append(0);
            sb.append(s);
        }
        return sb.toString();
    }

    private void hideLoading() {
        mViewLoadingContainer.setVisibility(View.GONE);
        mIvLoading.clearAnimation();
    }

    private void showLoading() {
        mViewLoadingContainer.setVisibility(View.VISIBLE);
        mIvLoading.startAnimation(loadingAnimation);
    }

}
