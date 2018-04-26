package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.YhlxBean;

/**
 * Created by issuser on 2018/4/25.
 */

public class CheckTypeAdapter extends QuickAdapter<YhlxBean> {

    public CheckTypeAdapter(Context context){
        super(context, R.layout.item_check_device);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, YhlxBean item, int position) {
        helper.setText(R.id.tv_name_device,item.getNAME_());
    }
}
