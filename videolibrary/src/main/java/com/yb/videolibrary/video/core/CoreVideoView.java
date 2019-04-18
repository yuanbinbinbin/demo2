package com.yb.videolibrary.video.core;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yb.videolibrary.video.inter.IVideoPlayControl;
import com.yb.videolibrary.video.inter.IVideoPlayListener;

import java.io.IOException;

class CoreVideoView extends SurfaceView implements IVideoPlayControl {

    private static final String TAG = "VideoView_COM";

    /**
     * MediaPlayer出错
     */
    private static final int STATE_ERROR = -1;
    /**
     * MediaPlayer的空闲状态
     */
    private static final int STATE_IDLE = 0;
    /**
     * MediaPlayer正在准备的状态
     */
    private static final int STATE_PREPARING = 1;
    /**
     * MediaPlayer准备好了的状态
     */
    private static final int STATE_PREPARED = 2;
    /**
     * MediaPlayer播放中的状态
     */
    private static final int STATE_PLAYING = 3;
    /**
     * MediaPlayer暂停的状态
     */
    private static final int STATE_PAUSED = 4;
    /**
     * MediaPlayer播放完成的状态
     */
    private static final int STATE_COMPLETION = 5;

    /**
     * 视频URI路径
     */
    //视频URI路径
    private Uri mUri;
    /**
     * 音频、视频文件播放时间总长度
     */
    //音频、视频文件播放时间总长度
    private int mDuration;

    /**
     * 上下文，或者说坏境变量
     */
    private Context mContext;

    // mCurrentState is a VideoView object's current state.
    // mTargetState is the state that a method caller intends to reach.
    // For instance, regardless the VideoView object's current state,
    // calling pause() intends to bring the object to a target state
    // of STATE_PAUSED.
    /**
     * 默认是空闲状态
     */
    private int mCurrentState = STATE_IDLE;
    /**
     * 默认是空闲状态
     */
    private int mTargetState = STATE_IDLE;

    // All the stuff we need for playing and showing a video
    /**
     * 播放和显示视频需要,是显示视频的帮助类。
     */
    private SurfaceHolder mSurfaceHolder = null;
    /**
     * 播放视频、音乐其实就是靠这个类。
     */
    private MediaPlayer mMediaPlayer = null;
    /**
     * 视频文件播放时显示宽度
     */
    private int mVideoWidth;
    /**
     * 视频文件播放时显示高度
     */
    private int mVideoHeight;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    /**
     * video播放状态回调
     */
    private IVideoPlayListener videoPlayListener;
    /**
     * 视频缓冲区的百分比
     */
    private int mCurrentBufferPercentage;

    private int mSeekWhenPrepared; // recording the seek position while
    /**
     * 是否能暂停
     */
    private boolean isPrepared;

    public CoreVideoView(Context context) {
        this(context, null);
    }

    public CoreVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoreVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initVideoView();
    }

    /**
     * 初始化VideoView，设置相关参数。
     */
    private void initVideoView() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        // 给SurfaceView当前的持有者一个回调对象。如果没有这个，表现是黑屏
        getHolder().addCallback(mSHCallback);
        // 下面设置SurfaceView不维护自己的缓冲区,而是等待屏幕的渲染引擎将内容推送到用户面前
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 通过setFocusable().来设置SurfaceView接受焦点的资格,
        setFocusable(true);
        // 对应在触摸模式下，你可以调用isFocusableInTouchMode().来获知是否有焦点来响应点触，
        // 也可以通过setFocusableInTouchMode().来设置是否有焦点来响应点触的资格.
        setFocusableInTouchMode(true);
        // 当用户请求在某个界面聚集焦点时，会调用requestFocus().这个方法。
        requestFocus();
        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
//        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
//        setMeasuredDimension(width, height);
//    }

    @Override
    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    @Override
    public void setVideoURI(Uri uri) {
        mUri = uri;
        mSeekWhenPrepared = 0;
        openVideo();
        //当某些变更导致视图的布局失效时调用该方法。该方法按照视图树的顺序调用。
        requestLayout();
        //更新视图
        invalidate();
    }

    @Override
    public void playOrPause() {
        if (isPlaying()) {
            pause();
        } else {
            start();
        }
    }

    @Override
    public void start() {
        if (isInPlaybackState()) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
            if (videoPlayListener != null) {
                videoPlayListener.onPlay();
            }
            mHandler.sendEmptyMessageDelayed(1, 100);
        }
        mTargetState = STATE_PLAYING;
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;
        }
        releaseUpdateProgress();
    }

    @Override
    public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
            }
        }
        mTargetState = STATE_PAUSED;
        if (videoPlayListener != null) {
            videoPlayListener.onPause();
        }
    }

    @Override
    public int getDuration() {
        if (isInPlaybackState()) {
            if (mDuration > 0) {
                return mDuration;
            }
            mDuration = mMediaPlayer.getDuration();
            return mDuration;
        }
        mDuration = -1;
        return mDuration;
    }

    @Override
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        if (isInPlaybackState()) {
            if (videoPlayListener != null) {
                videoPlayListener.onSeekStart();
            }
            mMediaPlayer.seekTo(pos);
            mSeekWhenPrepared = 0;
        } else {
            mSeekWhenPrepared = pos;
        }
    }

    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        return isPrepared;
    }

    @Override
    public boolean canSeekTo() {
        return isPrepared;
    }

    @Override
    public int getVideoWidth() {
        return mVideoWidth;
    }

    @Override
    public int getVideoHeight() {
        return mVideoHeight;
    }

    @Override
    public void registerPlayListener(IVideoPlayListener listener) {
        videoPlayListener = listener;
    }

    @Override
    public void activityResume() {

    }

    @Override
    public void activityPause() {

    }


    /**
     * 打开视频路径，并设置相关参数-该方法重要啊
     */
    private void openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            // not ready for playback just yet, will try again later
            return;
        }
        // Tell the music playback service to pause
        // framework.
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        mContext.sendBroadcast(i);

        // we shouldn't clear the target state, because somebody might have
        // called start() previously
        release(false);
        try {
            if (videoPlayListener != null) {
                videoPlayListener.onPreparing();
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
            mDuration = -1;
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
            mCurrentBufferPercentage = 0;
            mMediaPlayer.setDataSource(mContext, mUri);
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepareAsync();
            // we don't set the target state here either, but preserve the
            // target state that was there before.
            mCurrentState = STATE_PREPARING;
        } catch (IOException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer,
                    MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer,
                    MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        }
    }

    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            if (videoPlayListener != null) {
                videoPlayListener.onVideoSizeChange(mVideoWidth, mVideoHeight);
            }
        }
    };

    @Override
    public void resetHolderSize(int suggestWidth, int suggestHeight) {
        getHolder().setFixedSize(suggestWidth, suggestHeight);
    }

    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            if (videoPlayListener != null) {
                videoPlayListener.onPrepared();
            }
            mCurrentState = STATE_PREPARED;
            isPrepared = true;
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();

            int seekToPosition = mSeekWhenPrepared;
            if (seekToPosition != 0) {
                seekTo(seekToPosition);
            }
            if (mTargetState == STATE_PLAYING) {
                start();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            mCurrentState = STATE_COMPLETION;
            mTargetState = STATE_COMPLETION;
            if (videoPlayListener != null) {
                videoPlayListener.onPlayCompletion();
            }
            releaseUpdateProgress();
        }
    };

    private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            if (videoPlayListener != null) {
                String msg;
                if (impl_err == MediaPlayer.MEDIA_ERROR_IO || impl_err == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
                    msg = "网络出错";
                } else if (framework_err == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                    msg = "抱歉，该视频不适合在此设备上播放。";
                } else {
                    msg = "无法播放此视频。";
                }
                msg = msg + "(" + framework_err + "," + impl_err + ")";
                videoPlayListener.onError(framework_err, msg);
            }
            releaseUpdateProgress();
            return true;
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mCurrentBufferPercentage = percent;
            if (videoPlayListener != null) {
                videoPlayListener.onBufferingUpdate(percent);
            }
        }
    };

    private MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (videoPlayListener != null) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //卡了
                        videoPlayListener.onBlockStart();
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //不卡了
                        videoPlayListener.onBlockEnd();
                        break;
                }
            }
            return false;
        }
    };

    private MediaPlayer.OnSeekCompleteListener mSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mp) {
            if (videoPlayListener != null) {
                videoPlayListener.onSeekEnd();
            }
        }
    };

    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            mSurfaceWidth = w;
            mSurfaceHeight = h;
            boolean isValidState = (mTargetState == STATE_PLAYING);
            boolean hasValidSize = (mVideoWidth == w && mVideoHeight == h);
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared != 0) {
                    seekTo(mSeekWhenPrepared);
                }
                start();
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
            openVideo();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // after we return from this we can't use the surface any more
            mSurfaceHolder = null;
            if (mMediaPlayer != null) {
                mSeekWhenPrepared = mMediaPlayer.getCurrentPosition();
            }
            release(false);
        }
    };

    /**
     * release the media player in any state
     * 不管MediaPlayer是处于正在播放，还是暂停状态，只要你MediaPlayer存在，我就要MediaPlayer播放结束并处于空闲状态。
     */
    private void release(boolean cleartargetstate) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            if (cleartargetstate) {
                mTargetState = STATE_IDLE;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK
                && keyCode != KeyEvent.KEYCODE_VOLUME_UP
                && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN
                && keyCode != KeyEvent.KEYCODE_MENU
                && keyCode != KeyEvent.KEYCODE_CALL
                && keyCode != KeyEvent.KEYCODE_ENDCALL;
        if (isInPlaybackState() && isKeyCodeSupported) {
            if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                if (mMediaPlayer.isPlaying()) {
                    pause();
                } else {
                    start();
                }
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP && mMediaPlayer.isPlaying()) {
                pause();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 是否可以播放情况一切可好
     *
     * @return
     */
    private boolean isInPlaybackState() {
        return (mMediaPlayer != null && mCurrentState != STATE_ERROR && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING);
    }

    private void releaseUpdateProgress() {
        mHandler.removeCallbacksAndMessages(null);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (videoPlayListener == null || !isPlaying()) {
                return;
            }
            switch (msg.what) {
                case 1:
                    int total = getDuration();
                    if (getCurrentPosition() > 0) {
                        videoPlayListener.onPlayProgress(getCurrentPosition(), total, (int) (total * 1f / getBufferPercentage()));
                    } else {
                        videoPlayListener.onPlayProgress(0, total, (int) (total * 1f / getBufferPercentage()));
                    }
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    break;
                default:
                    break;
            }
        }
    };
}
