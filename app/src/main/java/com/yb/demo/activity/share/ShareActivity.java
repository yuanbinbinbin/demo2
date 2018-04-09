package com.yb.demo.activity.share;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.dialog.DialogManager;
import com.yb.demo.entity.dialog.ListItem;
import com.yb.demo.entity.share.ShareChannelInfo;
import com.yb.demo.utils.share.ShareController;

import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends BaseActivity {

    public static final int SHARE_TYPE_QQ = 1;
    public static final int SHARE_TYPE_WEIXIN = 2;
    public static final int SHARE_TYPE_QZONE = 3;
    private int shareType = SHARE_TYPE_QQ;
    private TextView mTvShare;
    private DialogManager dialogManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void initView() {
        mTvShare = (TextView) findViewById(R.id.id_activity_share_start);
    }

    @Override
    protected void initData() {
        dialogManager = new DialogManager(getContext());
    }

    @Override
    protected void initListener() {

    }

    public void selectFrom(View v) {
        if (shareType == SHARE_TYPE_QQ) {
            dialogManager.showDialog(DialogManager.DialogStyle.BOTTOM_SELECT_WIDTH_TITLE, new DialogManager.OnClickListenerContent() {
                @Override
                public void onClick(View view, Object... content) {
                    ShareChannelInfo info = (ShareChannelInfo) ((ListItem) content[1]).getItem();
                    if (info != null) {
                        if(info.getPackageName().equals("com.didapinche.booking")){
                            ShareController.getInstance().shareQQByOtherClient(getActivity(), "标题", "我是内容", "http://www.baidu.com?_wv=2", "http://img.zcool.cn/community/0124f358cec437a801219c77cd9b01.jpg", ShareController.Platform.QQ, info.getAppId(),info.getPackageName());
                        }else{
                            ShareController.getInstance().shareQQByOtherClient(getActivity(), "标题", "我是内容", "http://www.baidu.com", "http://img.zcool.cn/community/0124f358cec437a801219c77cd9b01.jpg", ShareController.Platform.QQ, info.getAppId(),info.getPackageName());
                        }
                    }
                }
            }, mTvShare.getText().toString(), getQQChanel());
        } else if (shareType == SHARE_TYPE_QZONE) {
            dialogManager.showDialog(DialogManager.DialogStyle.BOTTOM_SELECT_WIDTH_TITLE, new DialogManager.OnClickListenerContent() {
                @Override
                public void onClick(View view, Object... content) {
                    ShareChannelInfo info = (ShareChannelInfo) ((ListItem) content[1]).getItem();
                    if (info != null) {
                        ShareController.getInstance().shareQQByOtherClient(getActivity(), "标题", "我是内容", "http://www.baidu.com", "http://img.zcool.cn/community/0124f358cec437a801219c77cd9b01.jpg", ShareController.Platform.QZONE, info.getAppId(),info.getPackageName());
                    }
                }
            }, mTvShare.getText().toString(), getQQChanel());
        } else if (shareType == SHARE_TYPE_WEIXIN) {
            dialogManager.showDialog(DialogManager.DialogStyle.BOTTOM_SELECT_WIDTH_TITLE, new DialogManager.OnClickListenerContent() {
                @Override
                public void onClick(View view, Object... content) {
                    ShareChannelInfo info = (ShareChannelInfo) ((ListItem) content[1]).getItem();
                    if (info != null) {
                        ShareController.getInstance().shareWeiXinByOtherClient(getActivity(), "标题", "我是内容", "http://www.baidu.com", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), info.getAppId(), info.getPackageName(), ShareController.Platform.WEIXIN_FRIEND);
                    }
                }
            }, mTvShare.getText().toString(), getWeixinChanel());
        }
    }

    public void share2QQ(View v) {
        shareType = SHARE_TYPE_QQ;
        mTvShare.setText("分享到QQ");
    }

    public void share2Qzone(View v) {
        shareType = SHARE_TYPE_QQ;
        mTvShare.setText("分享到Qzone");
    }

    public void share2WeiXin(View v) {
        shareType = SHARE_TYPE_WEIXIN;
        mTvShare.setText("分享到微信");

    }

    public Object getQQChanel() {
        List<ListItem> itemList = new ArrayList<ListItem>();
        itemList.add(new ListItem("王者荣耀", new ShareChannelInfo("com.tencent.tmgp.sgame", "1104466820")));
        itemList.add(new ListItem("微信", new ShareChannelInfo("com.tencent.mm", "1103188687")));
        itemList.add(new ListItem("QQ兴趣部落", new ShareChannelInfo("com.tencent.tribe", "1104830192")));
        itemList.add(new ListItem("滴答拼车", new ShareChannelInfo("com.didapinche.booking", "1105560960")));

        return itemList;
    }

    public Object getWeixinChanel() {
        List<ListItem> itemList = new ArrayList<ListItem>();
        itemList.add(new ListItem("QQ", new ShareChannelInfo("com.tencent.mobileqq", "wxf0a80d0ac2e82aa7")));
        itemList.add(new ListItem("王者荣耀", new ShareChannelInfo("com.tencent.tmgp.sgame", "wx95a3a4d7c627e07d")));
        itemList.add(new ListItem("QQ浏览器", new ShareChannelInfo("com.tencent.mtt", "wx64f9cf5b17af074d")));
        itemList.add(new ListItem("QQ兴趣部落", new ShareChannelInfo("com.tencent.tribe", "wx350c755bfee8d760")));
        itemList.add(new ListItem("滴答拼车", new ShareChannelInfo("com.didapinche.booking", "wxd3a1541d4423bf8f")));
        return itemList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("test", "requestCode:" + requestCode);
    }
}
