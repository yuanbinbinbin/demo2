package com.yb.demo.utils.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.yb.demo.utils.DeviceUtil;
import com.yb.demo.utils.localapp.LocalAppUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ShareController {
    private static ShareController INSTANCE;

    private ShareController() {

    }

    public synchronized static ShareController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShareController();
        }
        return INSTANCE;

    }
//     6.1.0用到的分享
//    public void openDirShare(Context context, SHARE_MEDIA platform, String shareTitle, String shareContent, String targetUrl, String imgurl, Bitmap shareBitmap, File file, int imgId, UMShareListener umShareListener) {
//        UMImage umImage = null;
//        if (!TextUtils.isEmpty(imgurl)) {
//            umImage = new UMImage(context, imgurl);
//        } else if (shareBitmap != null) {
//            umImage = new UMImage(context, shareBitmap);
//        } else if (file != null) {
//            umImage = new UMImage(context, file);
//        } else if (imgId != -1) {
//            umImage = new UMImage(context, imgId);
//        }
//        ShareAction shareAction = new ShareAction((Activity) context)
//                .setPlatform(platform)
//                .setCallback(umShareListener)
//                .withTitle(shareTitle)
//                .withText(shareContent)
//                .withMedia(umImage)
//                .withTargetUrl(targetUrl);
//        shareAction.share();
//    }

    /**
     * 分享连接
     *
     * @param mContext
     * @param platform
     * @param shareTitle
     * @param shareContent
     * @param targetUrl
     * @param imgurl
     * @param shareBitmap
     * @param file
     * @param imgId
     * @param umShareListener
     */
//    public void shareLink(Context mContext, SHARE_MEDIA platform, String shareTitle, String shareContent, String targetUrl, String imgurl, Bitmap shareBitmap, File file, int imgId, UMShareListener umShareListener) {
//        try {
//            UMImage umImage = null;
//            if (!TextUtils.isEmpty(imgurl)) {
//                umImage = new UMImage(mContext, imgurl);
//            } else if (shareBitmap != null) {
//                umImage = new UMImage(mContext, shareBitmap);
//            } else if (file != null) {
//                umImage = new UMImage(mContext, file);
//            } else if (imgId != -1) {
//                umImage = new UMImage(mContext, imgId);
//            }
//            UMWeb web = new UMWeb(targetUrl);
//            web.setTitle(shareTitle);
//            web.setThumb(umImage);
//            web.setDescription(shareContent);
//            ShareAction shareAction = new ShareAction((Activity) mContext)
//                    .setPlatform(platform)
//                    .setCallback(umShareListener)
//                    .withText(shareContent)
//                    .withMedia(web);
//            shareAction.share();
//        } catch (Exception e) {
//        }
//    }

    /**
     * 分享图文
     *
     * @param mContext
     * @param platform
     * @param shareContent
     * @param imgurl
     * @param shareBitmap
     * @param file
     * @param imgId
     * @param umShareListener
     */
//    public void sharePicText(Context mContext, SHARE_MEDIA platform, String shareContent, String imgurl, Bitmap shareBitmap, File file, int imgId, UMShareListener umShareListener) {
//        try {
//            UMImage umImage = null;
//            if (!TextUtils.isEmpty(imgurl)) {
//                umImage = new UMImage(mContext, imgurl);
//            } else if (shareBitmap != null) {
//                umImage = new UMImage(mContext, shareBitmap);
//            } else if (file != null) {
//                umImage = new UMImage(mContext, file);
//            } else if (imgId != -1) {
//                umImage = new UMImage(mContext, imgId);
//            }
//            ShareAction shareAction = new ShareAction((Activity) mContext)
//                    .setPlatform(platform)
//                    .setCallback(umShareListener)
//                    .withText(shareContent)
//                    .withMedia(umImage);
//            shareAction.share();
//        } catch (Exception e) {
//        }
//    }

    /**
     * 通过本地组件分享
     *
     * @param mContext
     * @return
     */
    //通过本地组件分享
    public boolean shareByLocal(Activity mContext, ArrayList<Uri> paths, String content) {
        if ((paths == null || paths.size() <= 0) && TextUtils.isEmpty(content)) {
            return false;
        }
        if (mContext != null) {
            Intent intent = new Intent();
            if (paths == null || paths.size() == 0) {//文字分享
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/*");
            } else if (paths.size() == 1) {//一张图分享 或 一张图加文字分享
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, paths.get(0));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
            } else {//多张图分享 或 多张图加文字分享
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, paths);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
            }
            if (!TextUtils.isEmpty(content)) {
                intent.putExtra("Kdescription", content);//微信朋友圈，需要添加此字段，保证分享的文字能够被填充
                intent.putExtra(Intent.EXTRA_TEXT, content);
            }
            try {
                mContext.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 直接通过本地某个app分享
     *
     * @param packName
     * @param className
     * @param mContext
     * @return
     */
    //    直接通过本地某个app分享
    public boolean shareByLocalApp(String packName, String className, Activity mContext, ArrayList<Uri> paths, String content) {
        if ((paths == null || paths.size() <= 0) && TextUtils.isEmpty(content)) {
            return false;
        }
        if (!TextUtils.isEmpty(packName) && !TextUtils.isEmpty(className) && mContext != null) {
            Intent intent = new Intent();
            if (paths == null || paths.size() == 0) {//文字分享
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/*");
            } else if (paths.size() == 1) {//一张图分享 或 一张图加文字分享
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, paths.get(0));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
            } else {//多张图分享 或 多张图加文字分享
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, paths);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
            }
            if (!TextUtils.isEmpty(content)) {
                intent.putExtra("Kdescription", content);//微信朋友圈，需要添加此字段，保证分享的文字能够被填充
                intent.putExtra(Intent.EXTRA_TEXT, content);
            }
            intent.setComponent(new ComponentName(packName, className));
            try {
                mContext.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 通过本地QQ分享
     *
     * @param mContext
     * @return
     */
    //通过本地QQ分享
    public boolean shareQQByLocal(Activity mContext, ArrayList<Uri> paths, String content) {
        if (LocalAppUtils.isAppInstalled(mContext, "com.tencent.mobileqq")) {//QQ标准版、正常版本
            return shareByLocalApp("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity", mContext, paths, content);
        } else if (LocalAppUtils.isAppInstalled(mContext, "com.tencent.mobileqqi")) {//QQ国际版
            return shareByLocalApp("com.tencent.mobileqqi", "com.tencent.mobileqq.activity.JumpActivity", mContext, paths, content);
        } else if (LocalAppUtils.isAppInstalled(mContext, "com.tencent.qqlite")) {//QQ轻聊版
            return shareByLocalApp("com.tencent.qqlite", "com.tencent.mobileqq.activity.JumpActivity", mContext, paths, content);
        } else if (LocalAppUtils.isAppInstalled(mContext, "com.tencent.qq.kddi")) {//QQ日本版
            return shareByLocalApp("com.tencent.qq.kddi", "com.tencent.mobileqq.activity.JumpActivity", mContext, paths, content);
        }
        return shareByLocal(mContext, paths, content);
    }

    /**
     * 通过本地QQ空间分享
     *
     * @param mContext
     * @return
     */
    //通过本地QQ空间分享
    public boolean shareQZoneByLocal(Activity mContext, ArrayList<Uri> paths, String content) {
        if (LocalAppUtils.isAppInstalled(mContext, "com.qzone")) {//QQ空间、正常版本
            return shareByLocalApp("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity", mContext, paths, content);
        }
        return shareByLocal(mContext, paths, content);
    }

    /**
     * 通过本地微信分享
     *
     * @param mContext
     * @return
     */
    //通过本地微信分享
    public boolean shareWeiXinByLocal(Activity mContext, ArrayList<Uri> paths, String content) {
        if (LocalAppUtils.isAppInstalled(mContext, "com.tencent.mm")) {//微信标准版、正常版本
            return shareByLocalApp("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI", mContext, paths, content);
        }
        return shareByLocal(mContext, paths, content);
    }

    /**
     * 通过本地微信朋友圈分享
     *
     * @param mContext
     * @return
     */
    //通过本地微信朋友圈分享
    public boolean shareWeiXinCircleByLocal(Activity mContext, ArrayList<Uri> paths, String content) {
        if (LocalAppUtils.isAppInstalled(mContext, "com.tencent.mm")) {//微信标准版、正常版本
            return shareByLocalApp("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI", mContext, paths, content);
        }
        return shareByLocal(mContext, paths, content);
    }

    /**
     * 通过本地微博分享
     *
     * @param mContext
     * @return
     */
    //通过本地微博分享
    public boolean shareSinaByLocal(Activity mContext, ArrayList<Uri> paths, String content) {
        if (LocalAppUtils.isAppInstalled(mContext, "com.sina.weibo")) {//微博标准版、正常版本
            return shareByLocalApp("com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity", mContext, paths, content);
        } else if (LocalAppUtils.isAppInstalled(mContext, "com.weico.international")) {//微博国际版
            return shareByLocalApp("com.weico.international", "com.weico.international.activity.compose.SeaComposeActivity", mContext, paths, content);
        }
        return shareByLocal(mContext, paths, content);
    }

    /**
     * 伪装其他APP 分享到微信或微信朋友圈
     */
    //伪装其他APP  分享到微信或微信朋友圈
    public boolean shareWeiXinByOtherClient(Activity mContext, String shareTitle, String shareContent, String targetUrl, Bitmap thumbBitmap,
                                            String appId, String packageName, Platform platform) {
        try {
//            PushLogUtils.i("shareWeiXinByOtherClient", " start ShareWeixin By Other Client");
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = targetUrl;

            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = shareTitle;
            msg.description = shareContent;
            msg.setThumbImage(thumbBitmap);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            if (platform == Platform.WEIXIN_CIRCLE) {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;//微信朋友圈
            } else {
                req.scene = SendMessageToWX.Req.WXSceneSession;//微信好友
            }

            Bundle bundle = new Bundle();
            req.toBundle(bundle);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity"));
            intent.putExtras(bundle);
            intent.putExtra("_mmessage_sdkVersion", 570490883);
//            String packageName = "com.tencent.mobileqq";
//            String appId = "wxf0a80d0ac2e82aa7";
//            String packageName = "com.qike.feiyunlu.tv";
//            String appId = "wx38f124abbdd694d7";
//            String packageName = "com.tencent.mtt";
//            String appId = "wx64f9cf5b17af074d";
            intent.putExtra("_mmessage_appPackage", packageName);
            String url = "weixin://sendreq?appid=" + appId;
            intent.putExtra("_mmessage_content", url);

            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append(url);
            localStringBuffer.append(570490883);
            localStringBuffer.append(packageName);
            localStringBuffer.append("mMcShCsTr");
            intent.putExtra("_mmessage_checksum", com.tencent.mm.opensdk.a.b.e(localStringBuffer.toString().substring(1, 9).getBytes()).getBytes());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivityForResult(intent, 101);
        } catch (Exception e) {
            e.printStackTrace();
//            PushLogUtils.i("shareWeiXinByOtherClient", "ShareWeixin By Other Client Exception");
            return false;
        }
        return true;
    }

    public boolean shareQQByOtherClient(Activity mContext, String shareTitle, String shareContent, String targetUrl, String imageUrl, Platform platform, String shareId,String packageName) {
        if (TextUtils.isEmpty(targetUrl)) {
            return false;
        }
        try {
            Map<String, String> localHashMap = new HashMap<String, String>();
            localHashMap.put("image_url", baseEncode(imageUrl));
            localHashMap.put("title", baseEncode(shareTitle));
            localHashMap.put("description", baseEncode(shareContent));
            localHashMap.put("url", baseEncode(targetUrl));
            localHashMap.put("req_type", baseEncode("1"));
            if (platform == Platform.QZONE) {
                localHashMap.put("cflag", baseEncode("1"));
            }
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("mqqapi://share/to_fri?file_type=news&share_id=");
            localStringBuilder.append(shareId);
            localStringBuilder.append("&");
            localStringBuilder.append(map2String(localHashMap));

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(localStringBuilder.toString()));
            intent.putExtra("pkg_name", packageName);
//            intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
            mContext.startActivityForResult(intent,44);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private String baseEncode(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return "";
        }
        try {
            byte[] arrays = paramString.getBytes("utf-8");
            return Base64.encodeToString(arrays, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramString;
    }


    private String map2String(Map<String, String> paramMap) {
        StringBuilder localStringBuilder = new StringBuilder();
        Iterator localIterator = paramMap.keySet().iterator();
        while (localIterator.hasNext()) {
            String str1 = (String) localIterator.next();
            String str2 = paramMap.get(str1);
            if ((!TextUtils.isEmpty(str1)) && !TextUtils.isEmpty(str2)) {
                if (localStringBuilder.length() > 0) {
                    localStringBuilder.append("&");
                }
                localStringBuilder.append(str1);
                localStringBuilder.append("=");
                localStringBuilder.append(str2);
            }
        }
        String str1 = localStringBuilder.toString();
        return str1;
    }

    public enum Platform {
        WEIXIN_CIRCLE,
        WEIXIN_FRIEND,
        QQ,
        QZONE
    }
}
