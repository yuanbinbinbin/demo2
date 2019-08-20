package com.base.baselibrary.utils.video;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * desc:音视频分离<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/8/15 20:20
 */
public class VideoSeparateUtil {
    public static class MediaDivider {
        public static final String TAG = "MediaDivider";

        public final static String AUDIO_MIME = "audio";
        public final static String VIDEO_MIME = "video";


        public MediaDivider() {
        }

        public void divideMedia(String sourceMediaPath, String outPath, String divideMime) {
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(sourceMediaPath);
                int trackCount = mediaExtractor.getTrackCount();
                for (int i = 0; i < trackCount; i++) {
                    MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                    String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                    if (!mime.startsWith(divideMime)) {
                        continue;
                    }
                    int maxInputSize = mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    Log.d(TAG, "maxInputSize:" + maxInputSize);
                    ByteBuffer videoByteBuffer = ByteBuffer.allocate(maxInputSize);
                    if (divideMime.equals(AUDIO_MIME)) {
                        Log.d(TAG, "divide audio media to file");
                        String audioName = outPath + "/"
                                + sourceMediaPath.substring(sourceMediaPath.lastIndexOf('/') + 1, sourceMediaPath.lastIndexOf('.'))
                                + "_audio_out.mp4";
                        Log.d(TAG, "audioName:" + audioName);
                        MediaMuxer mediaMuxer = new MediaMuxer(audioName, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                        int audioTrack = mediaMuxer.addTrack(mediaFormat);
                        mediaMuxer.start();
                        divideToOutputAudio(mediaExtractor, mediaMuxer, videoByteBuffer, mediaFormat, audioTrack, i);
                        finish(mediaExtractor, mediaMuxer);
                        break;
                    } else if (divideMime.equals(VIDEO_MIME)) {
                        Log.d(TAG, "divide video media to file");
                        String videoName = outPath + "/"
                                + sourceMediaPath.substring(sourceMediaPath.lastIndexOf('/') + 1, sourceMediaPath.lastIndexOf('.'))
                                + "_video_out.mp4";
                        Log.d(TAG, "videoName:" + videoName);
                        MediaMuxer mediaMuxer = new MediaMuxer(videoName, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                        int videoTrack = mediaMuxer.addTrack(mediaFormat);
                        mediaMuxer.start();
                        divideToOutputVideo(mediaExtractor, mediaMuxer, videoByteBuffer, mediaFormat, videoTrack, i);
                        finish(mediaExtractor, mediaMuxer);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        private void divideToOutputVideo(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer, ByteBuffer byteBuffer, MediaFormat format,
                                         int videoTrack, int videoTrackIndex) {
            long videoDuration = format.getLong(MediaFormat.KEY_DURATION);
            mediaExtractor.selectTrack(videoTrackIndex);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.presentationTimeUs = 0;
            long videoFrameTimes;
            mediaExtractor.readSampleData(byteBuffer, 0);
            if (mediaExtractor.getSampleFlags() != MediaExtractor.SAMPLE_FLAG_SYNC) {
                mediaExtractor.advance();
            }
            mediaExtractor.readSampleData(byteBuffer, 0);
            mediaExtractor.advance();
            long firstFrame = mediaExtractor.getSampleTime();
            mediaExtractor.advance();
            mediaExtractor.readSampleData(byteBuffer, 0);
            long secondFrame = mediaExtractor.getSampleTime();
            videoFrameTimes = Math.abs(secondFrame - firstFrame);
            mediaExtractor.seekTo(0, MediaExtractor.SEEK_TO_CLOSEST_SYNC);
            int sampleSize;
            while ((sampleSize = mediaExtractor.readSampleData(byteBuffer, 0)) != -1) {
                long presentTime = bufferInfo.presentationTimeUs;
                if (presentTime >= videoDuration) {
                    mediaExtractor.unselectTrack(videoTrackIndex);
                    break;
                }
                mediaExtractor.advance();
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.size = sampleSize;
                mediaMuxer.writeSampleData(videoTrack, byteBuffer, bufferInfo);
                bufferInfo.presentationTimeUs += videoFrameTimes;
            }
            mediaExtractor.unselectTrack(videoTrackIndex);
        }

        private void divideToOutputAudio(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer, ByteBuffer byteBuffer, MediaFormat format,
                                         int audioTrack, int audioTrackIndex) {
            int sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
            int channelCount = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
            Log.d(TAG, "rate:" + sampleRate + ",c:" + channelCount);
            long audioDuration = format.getLong(MediaFormat.KEY_DURATION);
            mediaExtractor.selectTrack(audioTrackIndex);//参数为多媒体文件MediaExtractor获取到的track count的索引,选择音频轨道
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.presentationTimeUs = 0;
            long audioSampleSize;
            mediaExtractor.readSampleData(byteBuffer, 0);
            if (mediaExtractor.getSampleTime() == 0) {
                mediaExtractor.advance();
            }
            mediaExtractor.readSampleData(byteBuffer, 0);
            long firstRateSample = mediaExtractor.getSampleTime();
            mediaExtractor.advance();
            mediaExtractor.readSampleData(byteBuffer, 0);
            long secondRateSample = mediaExtractor.getSampleTime();
            audioSampleSize = Math.abs(secondRateSample - firstRateSample);
            mediaExtractor.seekTo(0, MediaExtractor.SEEK_TO_PREVIOUS_SYNC);
            int sampleSize;
            while ((sampleSize = mediaExtractor.readSampleData(byteBuffer, 0)) != -1) {
                int trackIndex = mediaExtractor.getSampleTrackIndex();
                long presentationTimeUs = bufferInfo.presentationTimeUs;
                Log.d(TAG, "trackIndex:" + trackIndex + ",presentationTimeUs:" + presentationTimeUs);
                if (presentationTimeUs >= audioDuration) {
                    mediaExtractor.unselectTrack(audioTrackIndex);
                    break;
                }
                mediaExtractor.advance();
                bufferInfo.offset = 0;
                bufferInfo.size = sampleSize;
                mediaMuxer.writeSampleData(audioTrack, byteBuffer, bufferInfo);//audioTrack为通过mediaMuxer.add()获取到的
                bufferInfo.presentationTimeUs += audioSampleSize;
            }
            mediaExtractor.unselectTrack(audioTrackIndex);
        }


        private void finish(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer) {
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaMuxer = null;
            mediaExtractor.release();
            mediaExtractor = null;
            Log.e(TAG, "finish");
        }
    }
}
