package com.yb.videolibrary.video.inter;

import android.net.Uri;

/**
 * desc:播放器控制接口<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2018/10/31 14:56
 */
public interface IVideoPlayControl {

    /**
     * 设置要播放音频、视频的路径。
     *
     * @param path 视频、音频路径
     */
    void setVideoPath(String path);

    /**
     * 设置要播放音频、视频的路径。
     *
     * @param uri 视频、音频路径
     */
    void setVideoURI(Uri uri);

    /**
     * 播放或暂停
     */
    void playOrPause();

    /**
     * 开始播放
     */
    void start();

    /**
     * 停止播放
     */
    void stop();

    /**
     * 暂停
     */
    void pause();

    /**
     * 获取总时长
     *
     * @return
     */
    int getDuration();

    /**
     * 获取当前播放位置
     *
     * @return
     */
    int getCurrentPosition();

    /**
     * 播放pos位置
     *
     * @param pos
     */
    void seekTo(int pos);

    /**
     * 是否在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 获取缓存进度
     *
     * @return
     */
    int getBufferPercentage();

    /**
     * 是否可以暂停
     *
     * @return
     */
    boolean canPause();

    /**
     * 是否可以快进
     *
     * @return
     */
    boolean canSeekTo();

    /**
     * 获取视频宽度
     *
     * @return
     */
    int getVideoWidth();

    /**
     * 获取视频高度
     *
     * @return
     */
    int getVideoHeight();

    /**
     * 设置播放宽高
     *
     * @param suggestWidth
     * @param suggestHeight
     */
    void resetHolderSize(int suggestWidth, int suggestHeight);

    /**
     * 注册播放监听
     *
     * @param listener
     */
    void registerPlayListener(IVideoPlayListener listener);

    /**
     * activity生命周期回调onResume，恢复播放
     */
    void activityResume();

    /**
     * activity生命周期回调onPause，暂停播放
     */
    void activityPause();
}
