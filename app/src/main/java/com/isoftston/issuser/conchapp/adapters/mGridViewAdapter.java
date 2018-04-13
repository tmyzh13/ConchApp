package com.isoftston.issuser.conchapp.adapters;

/**
 * Created by issuser on 2018/4/13.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.isoftston.issuser.conchapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by eyevision on 2016/2/16.
 */
public class mGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItem;


    public mGridViewAdapter(Context context, List<Map<String, Object>> listItem) {
        this.context = context;
        this.listItem = listItem;
    }


    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        Map<String, Object> map = listItem.get(position);
        imageView.setImageResource((Integer) map.get("ItemImage"));
        return convertView;
    }
}
