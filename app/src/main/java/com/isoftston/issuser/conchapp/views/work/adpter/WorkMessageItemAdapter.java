package com.isoftston.issuser.conchapp.views.work.adpter;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.utils.DateUtils;

/**
 * Created by issuser on 2018/4/16.
 */

public class WorkMessageItemAdapter extends QuickAdapter<WorkBean> {

    public WorkMessageItemAdapter(Context context) {
        super(context, R.layout.item_detail);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, WorkBean item, int position) {
//       String time=DateUtils.getDateToString(item.getCreateTime());
        helper.setText(R.id.item_title,item.getName());
//                .setText(R.id.tv_time, time);

    }
}
