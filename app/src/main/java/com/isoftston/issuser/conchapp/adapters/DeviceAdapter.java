package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.widget.TextView;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;

/**
 * Created by issuser on 2018/4/12.
 */

public class DeviceAdapter extends QuickAdapter<DeviceBean> {

    public DeviceAdapter(Context context){
        super(context, R.layout.item_device);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, DeviceBean item, int position) {
//        helper.setText(R.id.tv_device_name,item.name)
//                .setText(R.id.tv_time,item.createTime)
//                .setText(R.id.tv_device_no,item.equipCode)
//                .setText(R.id.tv_device_place,"");

    }
}
