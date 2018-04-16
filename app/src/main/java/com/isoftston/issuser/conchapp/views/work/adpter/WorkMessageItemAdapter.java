package com.isoftston.issuser.conchapp.views.work.adpter;

import android.content.Context;
import android.widget.TextView;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;

/**
 * Created by issuser on 2018/4/16.
 */

public class WorkMessageItemAdapter extends QuickAdapter<MessageBean> {

    public WorkMessageItemAdapter(Context context) {
        super(context, R.layout.item_detail);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MessageBean item, int position) {

        TextView item_title=helper.getView(R.id.item_title);
        TextView item_time=helper.getView(R.id.item_time);


    }
}
