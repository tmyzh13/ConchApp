package com.isoftston.issuser.conchapp.views.work.adpter;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.utils.DateUtils;

/**
 * Created by issuser on 2018/4/16.
 */

public class WorkMessageItemAdapter extends QuickAdapter<WorkDetailBean> {

    public WorkMessageItemAdapter(Context context) {
        super(context, R.layout.item_work_detail);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, WorkDetailBean item, int position) {
        String time=DateUtils.format_yyyy_MM_dd_HH_mm.format(item.getCreateTime());
        int status=item.getStatus();
        helper.setText(R.id.item_title,item.getName())
                .setText(R.id.item_time, time)
                .setText(R.id.content,item.getContent())
                .setText(R.id.work_adress,item.getPart());
        if (status==0){
            helper.setText(R.id.work_accept, context.getString(R.string.un_approved))
                    .setTextColor(R.id.work_accept,context.getResources().getColor(R.color.red));
            helper.setText(R.id.unclosed, context.getString(R.string.un_closed))
                    .setTextColor(R.id.unclosed,context.getResources().getColor(R.color.red));
        }else if (status==3){
            helper.setText(R.id.work_accept, context.getString(R.string.approved))
                    .setTextColor(R.id.work_accept,context.getResources().getColor(R.color.app_gray));
            helper.setText(R.id.unclosed, context.getString(R.string.un_closed))
                    .setTextColor(R.id.unclosed,context.getResources().getColor(R.color.red));
        }else if (status==5){
            helper.setText(R.id.work_accept, context.getString(R.string.approved))
                    .setTextColor(R.id.work_accept,context.getResources().getColor(R.color.app_gray));
            helper.setText(R.id.unclosed, context.getString(R.string.closed))
                .setTextColor(R.id.unclosed,context.getResources().getColor(R.color.app_gray));
        }else if (status==1){
            helper.setText(R.id.work_accept, context.getString(R.string.regret))
                    .setTextColor(R.id.work_accept,context.getResources().getColor(R.color.app_gray));
            helper.setText(R.id.unclosed, context.getString(R.string.closed))
                    .setTextColor(R.id.unclosed,context.getResources().getColor(R.color.red));
        }else if(status==2){
            helper.setText(R.id.work_accept, context.getString(R.string.approved))
                    .setTextColor(R.id.work_accept,context.getResources().getColor(R.color.app_gray));
            helper.setText(R.id.unclosed, context.getString(R.string.un_closed))
                    .setTextColor(R.id.unclosed,context.getResources().getColor(R.color.red));
        }


    }
}
