package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;

/**
 * Created by john on 2018/4/15.
 */

public class CheckPeopleAdapter extends QuickAdapter<CheckPeopleBean> {

    public CheckPeopleAdapter(Context context){
        super(context, R.layout.item_check_people);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CheckPeopleBean item, int position) {
        if (item.getOrgName()!=null){
            helper.setText(R.id.tv_name,item.getRealName()+"("+item.getOrgName()+")");
        }else {
            helper.setText(R.id.tv_name,item.getRealName()+"("+")");
        }

    }
}
