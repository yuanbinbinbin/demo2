package com.yb.demo.activity.dialog;

import android.view.View;
import android.widget.Toast;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.dialog.DialogManager;
import com.yb.demo.entity.dialog.ListItem;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends BaseActivity {

    DialogManager dialogManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        dialogManager = new DialogManager(getContext());
    }

    @Override
    protected void initListener() {

    }

    public void loading(View v) {
        dialogManager.showDialog(DialogManager.DialogStyle.LOADING, null, "我是可编辑的内容");
    }

    public void showTip(View v) {
        dialogManager.showDialog(DialogManager.DialogStyle.COMMON_INTOR, new DialogManager.OnClickListenerContent() {
            @Override
            public void onClick(View view, Object... content) {
                switch (view.getId()) {
                    case R.id.id_dialog_common_intro_cancel:
                        Toast.makeText(getContext(), "点击了左边的按钮", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_dialog_common_intro_ok:
                        Toast.makeText(getContext(), "点击了右边的按钮", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (dialogManager != null) {
                    dialogManager.dismissDialog();
                }
            }
        }, "标题", "内容", "左边", "右边");
    }

    public void showSelectNoTitle(View v) {
        List<ListItem> list = new ArrayList<ListItem>();
        for (int i = 0; i < 10; i++) {
            list.add(new ListItem("" + i, "Item:" + i));
        }
        dialogManager.showDialog(DialogManager.DialogStyle.BOTTOM_SELECT_NO_TITLE, new DialogManager.OnClickListenerContent() {
            @Override
            public void onClick(View view, Object... content) {
                switch (view.getId()) {
                    case R.id.dialog_list_lv:
                        if (content != null && content.length > 1) {
                            int position = 0;
                            if (content[0] != null && content[0] instanceof Integer) {
                                position = (int) content[0];
                            }
                            Toast.makeText(getContext(), "点击了" + position + " 内容： " + content[1], Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.dialog_list_tv_cancel:
                        Toast.makeText(getContext(), "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (dialogManager != null) {
                    dialogManager.dismissDialog();
                }
            }
        }, list);
    }

    public void showSelectTitle(View v) {
        List<ListItem> list = new ArrayList<ListItem>();
        for (int i = 0; i < 10; i++) {
            list.add(new ListItem("" + i, "Item:" + i));
        }
        dialogManager.showDialog(DialogManager.DialogStyle.BOTTOM_SELECT_WIDTH_TITLE, new DialogManager.OnClickListenerContent() {
            @Override
            public void onClick(View view, Object... content) {
                switch (view.getId()) {
                    case R.id.dialog_list_width_title_lv:
                        if (content != null && content.length > 1) {
                            int position = 0;
                            if (content[0] != null && content[0] instanceof Integer) {
                                position = (int) content[0];
                            }
                            Toast.makeText(getContext(), "点击了" + position + " 内容： " + content[1], Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.dialog_list_width_title_cancle:
                        Toast.makeText(getContext(), "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (dialogManager != null) {
                    dialogManager.dismissDialog();
                }
            }
        }, "我是可编辑的Title", list);
    }

    public void editDialog(View v) {
        dialogManager.showDialog(DialogManager.DialogStyle.SEND_MSG, new DialogManager.OnClickListenerContent() {
            @Override
            public void onClick(View view, Object... content) {
                switch (view.getId()) {
                    case R.id.dialog_btn_send:
                        Toast.makeText(getContext(), "填写的内容为:  " + content[0], Toast.LENGTH_SHORT).show();
                        break;
                }
                if (dialogManager != null) {
                    dialogManager.dismissDialog();
                }
            }
        }, "我是提示文字", "发送", -1);
    }
}
