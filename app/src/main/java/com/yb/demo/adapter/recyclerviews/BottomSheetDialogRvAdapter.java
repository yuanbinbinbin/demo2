package com.yb.demo.adapter.recyclerviews;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.baselibrary.adapter.recyclerview.RecyclerViewSingleItemBaseAdapter;
import com.yb.demo.R;

import java.util.List;

/**
 * Created by yb on 2017/9/4.
 */
public class BottomSheetDialogRvAdapter extends RecyclerViewSingleItemBaseAdapter<String, BottomSheetDialogRvAdapter.InnerViewHolder> {

    public BottomSheetDialogRvAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("test", "position: createViewHolder");
        return new InnerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bottom_sheet_dialog_rv, null));
    }

    @Override
    public void onBindViewHolder(InnerViewHolder holder, int position) {
        Log.e("test", "position:" + position);
        String content = getItem(position);
        if (holder != null && content != null) {
            holder.mTvText.setText(content);
        }
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        TextView mTvText;

        public InnerViewHolder(View itemView) {
            super(itemView);
            mTvText = (TextView) itemView.findViewById(R.id.id_item_bottom_sheet_dialog_rv_content);
        }
    }
}
