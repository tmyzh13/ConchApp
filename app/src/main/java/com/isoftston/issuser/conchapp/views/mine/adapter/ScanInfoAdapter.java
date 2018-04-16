package com.isoftston.issuser.conchapp.views.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.ScanInfo;

import java.util.List;

/**
 * Created by issuser on 2018/4/16.
 */

public class ScanInfoAdapter extends BaseAdapter {
    private Context context;
    private List<ScanInfo> datas;
    private LayoutInflater mInflater;

    public ScanInfoAdapter(Context context, List<ScanInfo> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScanInfo scanInfo = datas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_scan_info_list, null);
            holder.time = convertView.findViewById(R.id.last_time_scan_code);
            holder.address = convertView.findViewById(R.id.project_address_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time.setText(scanInfo.getScanTime());
        holder.address.setText(scanInfo.getAddress());
        return convertView;
    }

    public final class ViewHolder {
        public TextView time;
        public TextView address;
    }
}
