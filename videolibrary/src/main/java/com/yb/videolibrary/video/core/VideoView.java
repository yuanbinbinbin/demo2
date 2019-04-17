package com.yb.videolibrary.video.core;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yb.videolibrary.video.inter.IVideoPlayControl;
import com.yb.videolibrary.video.inter.IVideoPlayListener;


/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2018/11/1 14:01
 */
public class VideoView extends FrameLayout implements IVideoPlayControl {

    private Context mContext;
    private View videoView;
    private IVideoPlayControl innerControl;
    private boolean activityHasPaused;

    public VideoView(@NonNull Context context) {
        this(context, null);
    }

    public VideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initListener();
    }

    private void initData(Context context) {
        mContext = context;
        activityHasPaused = false;
    }

    private void initView() {
        CoreVideoView mVideoView = new CoreVideoView(mContext);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mVideoView.setLayoutParams(params);
        addView(mVideoView);
        innerControl = mVideoView;
        videoView = mVideoView;
    }

    private void initListener() {
        innerControl.registerPlayListener(innerVideoPlayListener);
    }


    @Override
    public void setVideoPath(String path) {
        if (innerControl != null) {
            innerControl.setVideoPath(path);
        }
    }

    @Override
    public void setVideoURI(Uri uri) {
        if (innerControl != null) {
            innerControl.setVideoURI(uri);
        }
    }

    @Override
    public void playOrPause() {
        if (innerControl != null) {
            innerControl.playOrPause();
        }
    }

    @Override
    public void start() {
        if (innerControl != null) {
            innerControl.start();
        }
    }

    @Override
    public void stop() {
        if (innerControl != null) {
            innerControl.stop();
        }
    }

    @Override
    public void pause() {
        if (innerControl != null) {
            innerControl.pause();
        }
    }

    @Override
    public int getDuration() {
        if (innerControl != null) {
            return innerControl.getDuration();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (innerControl != null) {
            innerControl.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        if (innerControl != null) {
            innerControl.seekTo(pos);
        }
    }

    @Override
    public boolean isPlaying() {
        if (innerControl != null) {
            return innerControl.isPlaying();
        }
        return false;
    }

    @Override
    public int getBufferPercentage() {
        if (innerControl != null) {
            return innerControl.getBufferPercentage();
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        if (innerControl != null) {
            return innerControl.canPause();
        }
        return false;
    }

    @Override
    public boolean canSeekTo() {
        if (innerControl != null) {
            return innerControl.canSeekTo();
        }
        return false;
    }

    @Override
    public int getVideoWidth() {
        if (innerControl != null) {
            return innerControl.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (innerControl != null) {
            return innerControl.getVideoHeight();
        }
        return 0;
    }

    @Override
    public void resetHolderSize(int suggestWidth, int suggestHeight) {
        if (innerControl != null) {
            innerControl.resetHolderSize(suggestWidth, suggestHeight);
        }
    }

    private void resetViewSize(int videoWidth, int videoHeight) {
        if (videoWidth > 0 && videoHeight > 0) {
            int widgetWidth = getMeasuredWidth();
            int widgetHeight = getMeasuredHeight();
            if (widgetWidth > 0 && widgetHeight > 0) {
                if (videoWidth == videoView.getMeasuredWidth() && videoHeight == videoView.getMeasuredHeight()) {
                    return;
                }
                float videoRatio = 1f * videoWidth / videoHeight;
                int resultWidth = 0;
                int resultHeight = 0;
                if (widgetWidth > videoWidth) {
                    //view 宽度大于视频宽度
                    resultHeight = (int) (widgetWidth / videoRatio);
                    if (resultHeight <= widgetHeight) {
                        resultWidth = widgetWidth;
                    } else {
                        resultWidth = (int) (widgetHeight * videoRatio);
                        resultHeight = widgetHeight;
                    }
                } else if (widgetWidth < videoWidth) {
                    //view宽度 小于视频宽度
                    resultHeight = (int) (widgetWidth / videoRatio);
                    if (resultHeight <= widgetHeight) {
                        resultWidth = widgetWidth;
                    } else {
                        resultWidth = (int) (widgetHeight * videoRatio);
                        resultHeight = widgetHeight;
                    }
                }
                innerControl.resetHolderSize(resultWidth, resultHeight);
                LayoutParams params = (LayoutParams) videoView.getLayoutParams();
                params.width = resultWidth;
                params.height = resultHeight;
                videoView.setLayoutParams(params);
            }
        }
    }

    @Override
    public void registerPlayListener(IVideoPlayListener listener) {
        videoPlayListener = listener;
    }

    @Override
    public void activityResume() {
        if (activityHasPaused) {
            activityHasPaused = false;
            start();
        }
    }

    @Override
    public void activityPause() {
        if (isPlaying()) {
            activityHasPaused = true;
            pause();
        }
    }

    private IVideoPlayListener videoPlayListener;
    private IVideoPlayListener innerVideoPlayListener = new IVideoPlayListener() {

        @Override
        public void onPreparing() {
            if (videoPlayListener != null) {
                videoPlayListener.onPreparing();
            }
        }

        @Override
        public void onPrepared() {
            if (videoPlayListener != null) {
                videoPlayListener.onPrepared();
            }
        }

        @Override
        public void onBufferingUpdate(int percent) {
            if (videoPlayListener != null) {
                videoPlayListener.onBufferingUpdate(percent);
            }
        }

        @Override
        public void onBlockStart() {
            if (videoPlayListener != null) {
                videoPlayListener.onBlockStart();
            }
        }

        @Override
        public void onBlockEnd() {
            if (videoPlayListener != null) {
                videoPlayListener.onBlockEnd();
            }
        }

        @Override
        public void onSeekStart() {
            if (videoPlayListener != null) {
                videoPlayListener.onSeekStart();
            }
        }

        @Override
        public void onSeekEnd() {
            if (videoPlayListener != null) {
                videoPlayListener.onSeekEnd();
            }
        }

        @Override
        public void onPlay() {
            if (videoPlayListener != null) {
                videoPlayListener.onPlay();
            }
        }

        @Override
        public void onPlayProgress(int progress, int maxProgress, int bufferProgress) {
            if (videoPlayListener != null) {
                videoPlayListener.onPlayProgress(progress, maxProgress, bufferProgress);
            }
        }

        @Override
        public void onPlayCompletion() {
            if (videoPlayListener != null) {
                videoPlayListener.onPlayCompletion();
            }
        }

        @Override
        public void onPause() {
            if (videoPlayListener != null) {
                videoPlayListener.onPause();
            }
        }

        @Override
        public void onError(int code, String msg) {
            if (videoPlayListener != null) {
                videoPlayListener.onError(code, msg);
            }
        }

        @Override
        public void onVideoSizeChange(int videoWidth, int videoHeight) {
            resetViewSize(videoWidth, videoHeight);
            if (videoPlayListener != null) {
                videoPlayListener.onVideoSizeChange(videoWidth, videoHeight);
            }
        }
    };
}
