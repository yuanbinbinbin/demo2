package com.yb.demo.activity.ascii;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.base.baselibrary.utils.PhotoSelectUtil;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_2_ascii;
    }

    @Override
    protected void initView() {
        mIvImage = (ImageView) findViewById(R.id.id_image);
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

    public void camera(View view) {
        photoSelectUtil.takePhoto();
    }

    @Override
    public void onImageSelect(final String imagePath) {
        Log.e("test", "imagePath: " + imagePath);
        new Thread(){
            @Override
            public void run() {
                final Bitmap bitmap =  AsciiUtil.createAsciiPic(imagePath,getContext());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIvImage.setImageBitmap(bitmap);
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
    public void onImageZoom(String imagePath) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFailure() {

    }
}
