package com.yb.demo.adapter.listviews.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yb.demo.R;
import com.yb.demo.entity.dialog.ListItem;

import java.util.List;

public class DialogListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ListItem> contents;
    private int textColor;
    OnDialogListItemClickListener onDialogListItemClickListener;

    public interface OnDialogListItemClickListener {
        void onDialogListItemClick(int position, Object object);
    }

    public void setOnReportClickListener(OnDialogListItemClickListener onDialogListItemClickListener) {
        this.onDialogListItemClickListener = onDialogListItemClickListener;
    }

    public DialogListAdapter(Context context, List<ListItem> contents, int textColor) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.contents = contents;
        if (textColor <= 0) {
            this.textColor = Color.parseColor("#999999");
        } else {
            this.textColor = textColor;
        }
    }

    @Override
    public int getCount() {
        return contents == null ? 0 : contents.size();
    }

    @Override
    public ListItem getItem(int position) {
        return position >= getCount() ? null : contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_dialog_list, null);
            holder = new ViewHolder();
            holder.tv_content = (TextView) convertView.findViewById(R.id.id_item_dialog_list_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setTextColor(textColor);
        holder.tv_content.setText(getContent(position));
        if (onDialogListItemClickListener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDialogListItemClickListener.onDialogListItemClick(position, getItem(position));
                }
            });
        }
        return convertView;
    }

    /**
     * 获取当前位置的内容
     *
     * @param position 当前的位置
     * @return 当前位置的内容
     */
    private String getContent(int position) {
        String content = contents.get(position).getContent();
        return content == null ? "" : content;
    }

    class ViewHolder {
        TextView tv_content;
    }
}
