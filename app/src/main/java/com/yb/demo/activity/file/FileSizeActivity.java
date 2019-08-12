package com.yb.demo.activity.file;

import android.util.Log;

import com.yb.demo.R;
import com.yb.demo.activity.BaseActivity;
import com.base.baselibrary.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileSizeActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_size;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Flod> results = new ArrayList<Flod>();
                File root = FileUtil.getOutDirPublic(getContext(), "");
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
                        return (int) (o1.getSize() - o2.getSize());
                    }
                });
                for (Flod flod : results) {
                    Log.e("test", "" + flod.getPath() + "  size: " + flod.getSize());
                }
            }
        }).start();
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
                            if ("/storage/emulated/0/UCDownloads/b".equals(childs[i].getAbsolutePath())) {
                                continue;
                            }
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

    }
}
