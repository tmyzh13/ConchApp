package com.isoftston.issuser.conchapp.views.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.ImageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.ScanInfo;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.views.message.ImageDetilActivity;
import com.isoftston.issuser.conchapp.views.work.ScanCodeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issuser on 2018/4/16.
 */

public class ScanInfoAdapter extends BaseAdapter {
    private Context context;
    private List<ImageInfoBean> datas;
    private LayoutInflater mInflater;
    private WorkDetailBean workDetailBean;

    public ScanInfoAdapter(Context context, List<ImageInfoBean> datas,WorkDetailBean workDetailBean) {
        this.context = context;
        this.datas = datas;
        this.workDetailBean=workDetailBean;
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
        ImageInfoBean scanInfo = datas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_scan_info_list, null);
            holder.time = convertView.findViewById(R.id.last_time_scan_code);
            holder.address = convertView.findViewById(R.id.project_address_tv);
            holder.lookPicture = convertView.findViewById(R.id.look_picture);
            holder.tv_provide_name=convertView.findViewById(R.id.tv_provide_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time.setText(DateUtils.format_MM_dd_HH_mm.format(scanInfo.getCreateTime()));
        holder.address.setText(scanInfo.getLocation());
        if (workDetailBean.gas!=null && scanInfo.getUserId().equals(workDetailBean.gas)){
                holder.tv_provide_name.setText(workDetailBean.gasName);
        }else if (scanInfo.getUserId().equals(workDetailBean.leading)){
            holder.tv_provide_name.setText(workDetailBean.leadingName);
        }else if (scanInfo.getUserId().equals(workDetailBean.guardian)){
            holder.tv_provide_name.setText(workDetailBean.guardianName);
        }else if (scanInfo.getUserId().equals(workDetailBean.auditor)){
                holder.tv_provide_name.setText(workDetailBean.auditorName);
        }else if(scanInfo.getUserId().equals(workDetailBean.approver)){
            holder.tv_provide_name.setText(workDetailBean.approverName);
        }
        final ArrayList<String> list = new ArrayList<>();
        String images=scanInfo.getImgs();
        if(images!=null){
            String[] itemPath=images.split("[,]");
            Log.i("imagePath","imagePath----"+itemPath[0].replace("\\","/"));
            for (int i = 0; i < itemPath.length; i++) {
                list.add(Urls.ROOT+itemPath[i].replace("\\","/"));
            }
        }

        holder.lookPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imagepath", list);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView time;
        public TextView address;
        public TextView lookPicture;
        public TextView tv_provide_name;
    }
}
