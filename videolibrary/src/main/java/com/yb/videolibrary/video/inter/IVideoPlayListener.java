package com.yb.videolibrary.video.inter;

/**
 * 视频播放回调
 */
public interface IVideoPlayListener {

    /**
     * 播放器开始准备
     */
    void onPreparing();

    /**
     * 播放器准备完成
     */
    void onPrepared();

    /**
     * 当播放视频时 暂停时的缓存进度10%
     *
     * @param percent
     */
    void onBufferingUpdate(int percent);

    /**
     * 卡了
     **/
    void onBlockStart();

    /**
     * 不卡了
     **/
    void onBlockEnd();

    /**
     * 开始跳转到指定位置
     */
    void onSeekStart();

    /**
     * 完成跳转到指定位置
     */
    void onSeekEnd();

    /**
     * 视频加载完成开始播放
     **/
    void onPlay();

    /**
     * 视频播放进度
     * 单位：ms
     */
    void onPlayProgress(int progress, int maxProgress, int bufferProgress);

    /**
     * 视频播放完成
     */
    void onPlayCompletion();

    /***暂停播放*/
    void onPause();

    /**
     * 播放出错
     *
     * @param code
     * @param msg
     */
    void onError(int code, String msg);

    /**
     * 视频大小发生改变
     *
     * @param width
     * @param height
     */
    void onVideoSizeChange(int width, int height);
}
