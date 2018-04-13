package com.isoftston.issuser.conchapp.views.message.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.CircleImageView;

import java.util.List;

/**
 * Created by issuser on 2018/4/9.
 */

public class MessageListviewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;

    public MessageListviewAdapter(Context context, List<String> lists) {
        this.context = context;
        this.list = lists;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_message, null);
            holder.imageView = convertView.findViewById(R.id.content_pic);
            holder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.item_mark = (ImageView) convertView.findViewById(R.id.item_mark);
            holder.item_title = (TextView) convertView.findViewById(R.id.item_title);
            holder.item_time = (TextView) convertView.findViewById(R.id.item_time);
            holder.content_msg = (TextView) convertView.findViewById(R.id.content);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.content_msg.setText(list.get(position));

        return convertView;
    }

    public final class ViewHolder {
        ImageView item_icon;
        ImageView item_mark;
        TextView item_title;
        TextView item_time;
        TextView content_msg;
        ImageView imageView;
        TextView address;
    }
}
