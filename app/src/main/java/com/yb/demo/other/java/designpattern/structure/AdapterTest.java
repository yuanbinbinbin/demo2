package com.yb.demo.other.java.designpattern.structure;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * 适配器模式<br>
 * 将数据转换成我们想要的形式<br>
 * 生活中的适配器:220V->充电器->36V<br>
 * ListView中的适配器模式：datas->adapter->view<br>
 * <b>以下代码不可运行，只是形式，没有意义</b><br>
 * Created by yb on 2017/12/26.
 */
public class AdapterTest {
    //Step1
    public static abstract class BaseCustomAdapter<T> {
        protected List<T> mList;

        protected T getItem(int position) {
            return getCount() <= position ? null : mList.get(position);
        }

        protected int getCount() {
            return mList == null ? 0 : mList.size();
        }

        protected abstract View getView(T data);
    }

    //Step2
    public static class CustomAdapter extends BaseCustomAdapter<Integer> {

        Context mContext;

        public CustomAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected View getView(Integer data) {
            TextView v = new TextView(mContext);
            v.setText("" + data);
            return v;
        }
    }

    public static class CustomAdapter2 extends BaseCustomAdapter<String> {

        Context mContext;

        public CustomAdapter2(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected View getView(String data) {
            TextView v = new TextView(mContext);
            v.setText(data);
            return v;
        }
    }

    //step3
    public static class CustomListView {
        private BaseCustomAdapter baseAdapter;

        public void setBaseAdapter(BaseCustomAdapter baseAdapter) {
            this.baseAdapter = baseAdapter;
        }

        public void updateView() {
            baseAdapter.getView(baseAdapter.getItem(0));
        }
    }

    public static void main(String[] args) {
        CustomListView listView = new CustomListView();
        listView.setBaseAdapter(new CustomAdapter(null));
//        listView.setBaseAdapter(new CustomAdapter2(null));

    }
}
