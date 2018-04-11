package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;

/**
 * Created by issuser on 2018/4/11.
 */

public class MessageTypeAdapter extends QuickAdapter<MessageBean> {
    public MessageTypeAdapter(Context context) {
        super(context, R.layout.item_message);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MessageBean item, int position) {


    }
}
