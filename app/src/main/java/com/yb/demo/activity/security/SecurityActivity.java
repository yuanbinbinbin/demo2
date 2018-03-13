package com.yb.demo.activity.security;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.base.baselibrary.utils.security.Base64Util;
import com.base.baselibrary.utils.security.MD5Util;
import com.base.baselibrary.utils.security.SecurityUtil;
import com.base.baselibrary.utils.security.URLUtil;
import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.yb.demo.utils.ToastUtil;

public class SecurityActivity extends BaseActivity {

    private EditText mEtContent;
    private TextView mTvResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security;
    }

    @Override
    protected void initView() {
        mEtContent = (EditText) findViewById(R.id.id_activity_security_content);
        mTvResult = (TextView) findViewById(R.id.id_activity_security_result);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void test(View view) {
        String content = mEtContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShortTime(getContext(), "内容不能为空...");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("urlEncode: \n");
        sb.append(URLUtil.encode(content));
        sb.append("\n\nurlDecode: \n");
        sb.append(URLUtil.decode(URLUtil.encode(content)));

        sb.append("\n\nMD5: \n");
        sb.append(MD5Util.md5(content, "UTF-8"));

        sb.append("\n\nBase64Encode: \n");
        sb.append(Base64Util.encodeToString(content.getBytes(), Base64Util.DEFAULT));
        sb.append("\n\nBase64Decode: \n");
        sb.append(Base64Util.decodeToString(Base64Util.encodeToString(content.getBytes(), Base64Util.DEFAULT), Base64Util.DEFAULT));


        sb.append("\n\nencrypt: \n");
        sb.append(SecurityUtil.encrypt(content));
        sb.append("\n\ndecrypt: \n");
        sb.append(SecurityUtil.decrypt(SecurityUtil.encrypt(content)));

        mTvResult.setText(sb.toString());
    }
}
