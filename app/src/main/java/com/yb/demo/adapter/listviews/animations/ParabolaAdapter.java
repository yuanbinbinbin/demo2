package com.yb.demo.adapter.listviews.animations;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.base.baselibrary.adapter.listview.ListViewSingleItemBaseAdapter;
import com.base.baselibrary.adapter.listview.ListViewViewHolder;
import com.yb.demo.R;

/**
 * Created by yb on 2017/11/29.
 */
public class ParabolaAdapter extends ListViewSingleItemBaseAdapter<Integer, ParabolaAdapter.ViewHolder> {

    public ParabolaAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    protected ViewHolder createView() {
        View convertView = getView(R.layout.item_parabola);
        if (convertView == null) {
            return null;
        }
        ViewHolder vh = new ViewHolder(convertView);
        return vh;
    }

    @Override
    protected void updateView(final Integer item, final int position, ViewHolder viewHolder) {
        if (viewHolder != null) {
            viewHolder.mIv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, position, item);
                }
            });
            viewHolder.mIv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, position, item);
                }
            });
            viewHolder.mIv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, position, item);
                }
            });
        }
    }

    class ViewHolder extends ListViewViewHolder {
        ImageView mIv1;
        ImageView mIv2;
        ImageView mIv3;

        public ViewHolder(View itemView) {
            super(itemView);
            mIv1 = (ImageView) itemView.findViewById(R.id.id_item_parabola_iv1);
            mIv2 = (ImageView) itemView.findViewById(R.id.id_item_parabola_iv2);
            mIv3 = (ImageView) itemView.findViewById(R.id.id_item_parabola_iv3);
        }
    }
}
