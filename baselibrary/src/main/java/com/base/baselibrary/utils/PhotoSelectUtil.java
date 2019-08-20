package com.base.baselibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.base.baselibrary.utils.provider.BaseFileProviderUtils;

import java.io.File;
import java.net.URI;


public class PhotoSelectUtil {

    //拍照action
    public static final int TAKE_PHOTO_ACTION = 1;
    //选择相册中的图片action
    public static final int SELECT_PHOTO_ACTION = 2;
    //图片裁剪action
    public static final int CUT_PHOTO_ACTION = 3;
    //选择视频
    public static final int SELECT_VIDEO_ACTION = 4;

    private Activity mContext;
    private Uri mImageUri;
    private Uri tempUri;


    public PhotoSelectUtil(Activity context) {
        mContext = context;
    }

    /**
     * 调取系统相机
     */
    public void takePhoto() {
        try {
            createImageUri();
            // 用相机拍照
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(getFileUri(tempUri)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            mContext.startActivityForResult(intent, TAKE_PHOTO_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "系统相机未找到", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从相册中选择图片
     */
    public void pickAlbum() {
        try {
            createImageUri();
            Intent select_intent = new Intent(Intent.ACTION_PICK, null);
            select_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            select_intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            select_intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            mContext.startActivityForResult(select_intent, SELECT_PHOTO_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "系统相册未找到", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickVideo() {
        try {
            createImageUri();
            Intent select_intent = new Intent(Intent.ACTION_PICK, null);
            select_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            select_intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            select_intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
            mContext.startActivityForResult(select_intent, SELECT_VIDEO_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "系统相册未找到", Toast.LENGTH_SHORT).show();
        }
    }

    private void createImageUri() {
        String picName = "";
        picName = picName + System.currentTimeMillis();
        String parentPath = getOutDirPublic(mContext, "photo").getAbsolutePath();
        String path = parentPath + File.separator + picName + "_.jpg";
        tempUri = Uri.fromFile(new File(path));
        path = parentPath + File.separator + picName + ".jpg";
        mImageUri = Uri.fromFile(new File(path));
    }

    /**
     * 图片裁剪
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param outUri
     */
    public void photoZoom(Uri uri, int outputX, int outputY, Uri outUri) {
        this.tempUri = uri;
        this.mImageUri = outUri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getFileUri(getFileUri(uri)), "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", outputX);
        intent.putExtra("aspectY", outputY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);//防止裁剪图片过小有黑边
        intent.putExtra("scaleUpIfNeeded", true);//防止裁剪图片过小有黑边
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("return-data", false);
        try {
            mContext.startActivityForResult(intent, CUT_PHOTO_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "系统截图未找到", Toast.LENGTH_SHORT).show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            if (mListener != null) {
                mListener.onCancel();
            }
            return;
        }
        if (requestCode == SELECT_PHOTO_ACTION) {
            //相册
            if (data != null) {
                if (mListener != null) {
                    mListener.onImageSelect(getRealFilePath(mContext, data.getData()));
                }
            } else {
                if (mListener != null) {
                    mListener.onFailure();
                }
            }
        } else if (requestCode == TAKE_PHOTO_ACTION) {
            //拍照
            updateGallery(mContext, tempUri.getPath());
            if (mListener != null) {
                mListener.onImageSelect(tempUri.getPath());
            }
        } else if (requestCode == CUT_PHOTO_ACTION) {
            //裁剪
            if (mListener != null) {
                mListener.onImageZoom(mImageUri.getPath());
            }
            updateGallery(mContext, mImageUri.getPath());
        } else if (requestCode == SELECT_VIDEO_ACTION) {
            if (data != null) {
                if (mListener != null) {
                    mListener.onVideoSelect(getRealFilePath(mContext, data.getData()));
                }
            } else {
                if (mListener != null) {
                    mListener.onFailure();
                }
            }
        }
    }

    private OnSystemHandleImageSaveListener mListener;

    public void setOnImageSaveListener(OnSystemHandleImageSaveListener listener) {
        mListener = listener;
    }

    private String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return "";
        final String scheme = uri.getScheme();
        String data = "";
        try {
            if (scheme == null)
                data = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
        } catch (Throwable t) {

        }
        return data;
    }

    //    系统处理Image回调
    public interface OnSystemHandleImageSaveListener {
        /**
         * 图片选择回调
         *
         * @param imagePath
         */
        void onImageSelect(String imagePath);

        void onVideoSelect(String videoPath);

        /**
         * 图片剪切回调
         *
         * @param imagePath
         */
        void onImageZoom(String imagePath);

        //用户取消
        void onCancel();

        void onFailure();
    }

    //本地媒体数据库更新
    public static void updateGallery(Context context, String filename) {
        try {
            MediaScannerConnection.scanFile(context,
                    new String[]{filename}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } catch (Throwable t) {
        }
    }


    //region File Util
    private File getOutDirPublic(Context context, String path) {
        if (isExternalPathExist(context)) {
            File file = Environment.getExternalStoragePublicDirectory(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    private boolean isExternalPathExist(Context context) {
        //Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);判断外部存储器是否挂载
        return haveMountedPermission(context) && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private boolean haveMountedPermission(Context context) {
        try {
            String[] array = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            for (String permi : array) {
                if (permi.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    private Uri getFileUri(File file) {
        return BaseFileProviderUtils.getUriForFile(mContext, file);
    }

    private File getFileUri(Uri uri) {
        try {
            return new File(new URI(uri.toString()));
        } catch (Throwable t) {

        }
        return null;
    }
    //endregion
}
