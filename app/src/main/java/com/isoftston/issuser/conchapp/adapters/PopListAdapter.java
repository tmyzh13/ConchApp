package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;

/**
 * Created by john on 2018/4/15.
 */

public class PopListAdapter extends QuickAdapter<String> {

    public PopListAdapter(Context context){
        super(context, R.layout.item_pop_list);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, String item, int position) {
        helper.setText(R.id.tv_content,item);
    }
}
