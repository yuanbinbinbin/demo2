package com.yb.demo.activity.permission;

import android.view.View;

import com.base.baselibrary.utils.permission.PermissionHelper;
import com.base.baselibrary.utils.permission.lib.Permission;
import com.base.baselibrary.utils.permission.lib.inter.Action;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.ToastUtil;

import java.util.List;

public class PermissionActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void go2SettingPermission(View view) {
        PermissionHelper.permissionSetting(this).execute();
    }

    public void sdPermission(final View view) {
        PermissionHelper.with(this)
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showShortTime(getContext(), "获得SD card权限");
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showShortTime(getContext(), "SD card权限被拒绝");
                        sdPermission(view);
                    }
                }).start();
    }

    public void LocationPermission(View view) {
        PermissionHelper.with(this)
                .permission(Permission.Group.LOCATION)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showShortTime(getContext(), "获得Location权限");
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showShortTime(getContext(), "没有获得 Location权限");
                    }
                })
                .start();
    }
}
