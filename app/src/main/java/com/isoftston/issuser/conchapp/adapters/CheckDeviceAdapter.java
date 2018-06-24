package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.view.View;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;

/**
 * Created by issuser on 2018/4/25.
 */

public class CheckDeviceAdapter extends QuickAdapter<DeviceTypeBean> {

    public CheckDeviceAdapter(Context context){
        super(context, R.layout.item_check_device);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, DeviceTypeBean item, int position) {
        helper.setText(R.id.tv_name_device,item.getName());

    }
}
