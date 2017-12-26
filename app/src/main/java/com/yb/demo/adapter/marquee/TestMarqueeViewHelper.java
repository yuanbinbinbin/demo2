package com.yb.demo.adapter.marquee;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.entity.animations.marquee.MarqueeViewTestBean;
import com.yb.demo.weights.marquee.MarqueeView;

/**
 * Created by yb on 2017/12/8.
 */
public class TestMarqueeViewHelper extends MarqueeView.MarqueeViewBaseAdapter<MarqueeViewTestBean> {
    @Override
    public View createView(Context context) {
        if (context == null) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_img_text, null);
        ViewHolder holder = new ViewHolder();
        holder.mIv = (ImageView) view.findViewById(R.id.id_item_img_text_img);
        holder.mTv = (TextView) view.findViewById(R.id.id_item_img_text_tv);
        view.setTag(holder);
        return view;
    }

    @Override
    public void updateView(View view, MarqueeViewTestBean marqueeViewTestBean) {
        if (view == null || marqueeViewTestBean == null) {
            return;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mIv.setImageResource(marqueeViewTestBean.getImgRes());
        holder.mTv.setText(marqueeViewTestBean.getText());
    }

    class ViewHolder {
        ImageView mIv;
        TextView mTv;
    }
}
