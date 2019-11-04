package com.yb.demo.activity.file;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.base.baselibrary.utils.FileUtil;
import com.yb.demo.weights.ResultListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSizeActivity extends BaseActivity {

    public static void start(Context context, String path) {
        Intent starter = new Intent(context, FileSizeActivity.class);
        starter.putExtra("path", path);
        context.startActivity(starter);
    }

    class Flod {
        String path;
        float size;

        public Flod(String path, float size) {
            this.path = path;
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public float getSize() {
            return size;
        }

        public void setSize(float size) {
            this.size = size;
        }
    }

    private ResultListView mRLv;
    private TextView mTvTitle;
    private String path;
    List<Flod> flods;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_size;
    }

    @Override
    protected void initView() {
        mRLv = (ResultListView) findViewById(R.id.id_activity_file_size_list);
        mTvTitle = (TextView) findViewById(R.id.id_activity_file_size_title);
    }

    @Override
    protected void initData() {
        path = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path)) {
            path = FileUtil.getOutDirPublic(getContext(), "").getAbsolutePath();
        }
        mTvTitle.setText(path);
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Flod> results = new ArrayList<Flod>();
                File root = new File(path);
                if (root.isDirectory()) {
                    File[] childs = root.listFiles();
                    if (childs != null && childs.length > 0) {
                        for (int i = 0; i < childs.length; i++) {
                            results.add(new Flod(childs[i].getAbsolutePath(), ((int) (getSizeM(childs[i]) * 100)) * 1.0f / 100));
                        }
                    }
                }
                Collections.sort(results, new Comparator<Flod>() {
                    @Override
                    public int compare(Flod o1, Flod o2) {
                        if (o1.size > o2.size) {
                            return -1;
                        } else if (o1.size == o2.size) {
                            return 0;
                        }
                        return 1;
                    }
                });
                for (Flod flod : results) {
                    Log.e("test", "" + flod.getPath() + "  size: " + flod.getSize());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fileData(results);
                    }
                });
            }
        }).start();
    }

    private void fileData(List<Flod> results) {
        dismissLoading();
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        for (Flod result : results) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("path", new File(result.getPath()).getName());
            data.put("size", result.getSize() + " M");
            datas.add(data);
        }
        mRLv.setAdapter(new SimpleAdapter(this, datas, R.layout.item_file_size,
                new String[]{"path", "size"},
                new int[]{R.id.id_item_file_size_path, R.id.id_item_file_size_size}));

        flods = results;
    }

    private double getSizeM(File root) {
        double result = 0;
        if (root != null) {
            if (root.isDirectory()) {
                try {
                    File[] childs = root.listFiles();
                    if (childs != null && childs.length > 0) {
                        for (int i = 0; i < childs.length; i++) {
//                            Log.e("test", "exception " + childs[i].getAbsolutePath());
                            if (childs[i].isDirectory()) {
                                result += getSizeM(childs[i]);
                            } else {
                                result += 1.0 * childs[i].length() / 1024 / 1024;
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else {
                result += 1.0 * root.length() / 1024 / 1024;
            }
        }
        return result;
    }

    private void printPath(File root) {
        if (root != null) {
            Log.e("test", "" + root.getAbsolutePath() + "  size: " + 1.0f * root.length() / 1024 / 1024);
            if (root.isDirectory()) {
                File[] childs = root.listFiles();
                if (childs != null && childs.length > 0) {
                    for (int i = 0; i < childs.length; i++) {
                        printPath(childs[i]);
                    }
                }
            }
        }
    }

    @Override
    protected void initListener() {
        mRLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flod flod = flods.get(position - 1);
                File file = new File(flod.path);
                if (file.isDirectory()) {
                    FileSizeActivity.start(getContext(), flod.path);
                } else {
                    toast(file.getName() + " 不是文件夹");
                }
            }
        });
    }
}
