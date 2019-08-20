package com.yb.demo.activity.ascii;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.base.baselibrary.utils.FileUtil;
import com.base.baselibrary.utils.PhotoSelectUtil;
import com.base.baselibrary.utils.video.VideoSeparateUtil;
import com.base.baselibrary.utils.video.videoCreator.IProvider;
import com.base.baselibrary.utils.video.videoCreator.handler.CreatorExecuteResponseHander;
import com.base.baselibrary.utils.video.videoCreator.task.AvcExecuteAsyncTask;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.SafeConvertUtil;
import com.yb.demo.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * desc:<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2019/8/8 16:59
 */
public class Pic2AsciiActivity extends BaseActivity implements PhotoSelectUtil.OnSystemHandleImageSaveListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, Pic2AsciiActivity.class);
        context.startActivity(starter);
    }

    PhotoSelectUtil photoSelectUtil;
    ImageView mIvImage;
    TextView mTvVideo;
    RadioGroup colorPicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_2_ascii;
    }

    @Override
    protected void initView() {
        mIvImage = (ImageView) findViewById(R.id.id_image);
        mTvVideo = (TextView) findViewById(R.id.id_video);
        colorPicker = (RadioGroup) findViewById(R.id.id_color_picker_group);
    }

    @Override
    protected void initData() {
        photoSelectUtil = new PhotoSelectUtil(getActivity());
    }

    @Override
    protected void initListener() {
        photoSelectUtil.setOnImageSaveListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoSelectUtil.onActivityResult(requestCode, resultCode, data);
    }

    public void album(View view) {
        photoSelectUtil.pickAlbum();
    }

    public void video(View view) {
        photoSelectUtil.pickVideo();
    }

    public void camera(View view) {
        photoSelectUtil.takePhoto();
    }

    private boolean isGreySelect() {
        return colorPicker.getCheckedRadioButtonId() == R.id.id_color_picker_group_grey;
    }

    //region photo select
    @Override
    public void onImageSelect(final String imagePath) {
        Log.e("test", "imagePath: " + imagePath);
        new Thread() {
            @Override
            public void run() {
                boolean isGrey = isGreySelect();
                final Bitmap bitmap = isGrey
                        ? AsciiUtil.createAsciiPic(imagePath, getContext())
                        : AsciiUtil.createAsciiPicColor(imagePath, getContext());
                //region 图片保存
                File file = new File(imagePath);
                String name = file.getName();
                if (name.contains(".")) {
                    name = name.substring(0, name.indexOf("."));
                }
                name = name + (isGrey ? "_grey" : "_color");
                final File newFile = new File(FileUtil.getOutDirPublic(getContext(), "ascii").getAbsolutePath() + File.separator + name + ".jpg");
                try {
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    FileOutputStream fos = new FileOutputStream(newFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (Throwable e) {


                }
                //endregion
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PhotoSelectUtil.updateGallery(getContext(), newFile.getAbsolutePath());
                        mIvImage.setImageBitmap(bitmap);
                        ToastUtil.showShortTime(getContext(), "成功");
                    }
                });
            }
        }.start();
        // 用option设置返回的bitmap对象的一些属性参数
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;// 设置仅读取Bitmap的宽高而不读取内容
//        BitmapFactory.decodeFile(imagePath, options);// 获取到图片的宽高，放在option里边
//        final int height = options.outHeight;//图片的高度放在option里的outHeight属性中
//        final int width = options.outWidth;
//        int rqsW = mIvImage.getMeasuredWidth();
//        int rqsH = mIvImage.getMeasuredHeight();
//        int inSampleSize = 1;
//        if (rqsW == 0 || rqsH == 0) {
//            options.inSampleSize = 1;
//        } else if (height > rqsH || width > rqsW) {
//            final int heightRatio = Math.round((float) height / (float) rqsH);
//            final int widthRatio = Math.round((float) width / (float) rqsW);
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//            options.inSampleSize = inSampleSize;
//        }
//        options.inJustDecodeBounds = false;
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);// 主要通过option里的inSampleSize对原图片进行按比例压缩
//        mIvImage.setImageBitmap(bitmap);
    }

    @Override
    public void onVideoSelect(final String videoPath) {
        Log.e("test", "videoPath: " + videoPath);
        File file = new File(videoPath);
        boolean isGrey = isGreySelect();
        String name = file.getName();
        if (name.contains(".")) {
            name = name.substring(0, name.indexOf("."));
        }
        name = name + (isGrey ? "_grey" : "_color");
        final File newFile = new File(FileUtil.getOutDirPublic(getContext(), "ascii").getAbsolutePath() + File.separator + name + ".mp4");
        AvcExecuteAsyncTask.execute(new BitmapProvider(videoPath, 30), 30, new CreatorExecuteResponseHander() {
            @Override
            public void onSuccess(Object message) {
                Log.e("test", "create video success: " + message.toString());
                ToastUtil.showShortTime(getContext(), "成功");
                PhotoSelectUtil.updateGallery(getContext(), newFile.getAbsolutePath());
            }

            @Override
            public void onProgress(Object message) {
                Log.e("test", "create video progress: " + message.toString());
                mTvVideo.setText("video: " + message.toString() + "%");
            }

            @Override
            public void onFailure(Object message) {
                Log.e("test", "create video onFailure: " + message.toString());
            }

            @Override
            public void onStart() {
                Log.e("test", "create video onStart");
            }

            @Override
            public void onFinish() {
                Log.e("test", "create video onFinish");
            }
        }, newFile.getAbsolutePath());
    }

    @Override
    public void onImageZoom(String imagePath) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFailure() {

    }
    //endregion

    class BitmapProvider implements IProvider<Bitmap> {
        private int mFrameRate;
        private int index;
        private int frameCount;
        MediaMetadataRetriever mmr;

        public BitmapProvider(String path, int fps) {
            this.mFrameRate = (int) Math.ceil(1000f / fps);
            index = 0;
            mmr = new MediaMetadataRetriever();
            try {
                mmr.setDataSource(path);
                String d = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
                long duration = SafeConvertUtil.convertToInt(d, 0);
                frameCount = (int) (duration / mFrameRate);
                Log.d("test", "duration:" + d + "frame Count" + frameCount);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @Override
        public boolean hasNext() {
            return size() > 0;
        }

        @Override
        public int size() {
            return frameCount <= 0 ? 0 : frameCount;
        }

        @Override
        public Bitmap next() {
            Bitmap b = mmr.getFrameAtTime((index * mFrameRate) * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
            final Bitmap bitmap = isGreySelect()
                    ? AsciiUtil.createAsciiPic(b, getContext())
                    : AsciiUtil.createAsciiPicColor(b, getContext());
            ;
//            final Bitmap bitmap = mmr.getFrameAtTime((index * mFrameRate) * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
            index++;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mIvImage.setImageBitmap(bitmap);
                }
            });
            return bitmap;
        }
    }
}
