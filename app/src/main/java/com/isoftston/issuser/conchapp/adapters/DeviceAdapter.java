package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by issuser on 2018/4/12.
 */

public class DeviceAdapter extends QuickAdapter<DeviceBean> {


    public DeviceAdapter(Context context){
        super(context, R.layout.item_device);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, DeviceBean item, int position) {
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new java.util.Date(
                        Long.parseLong(item.getCreateTime())));
        helper.setText(R.id.tv_device_name,item.getName())
                .setText(R.id.tv_time, date)
                .setText(R.id.tv_device_no,item.getEquipCode())
                .setText(R.id.tv_device_place,item.getLocation());

    }
}
