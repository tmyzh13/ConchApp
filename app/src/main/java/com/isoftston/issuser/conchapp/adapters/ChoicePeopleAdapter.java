package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;

/**
 * Created by issuser on 2018/5/3.
 */

public class ChoicePeopleAdapter extends QuickAdapter<CheckPeopleBean> {

    public ChoicePeopleAdapter(Context context) {
        super(context, R.layout.item_check_people);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CheckPeopleBean item, int position) {
        helper.setText(R.id.tv_name,item.getRealName());

    }
}
