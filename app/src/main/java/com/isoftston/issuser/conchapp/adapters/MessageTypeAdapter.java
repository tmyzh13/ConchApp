package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.corelibs.views.roundedimageview.RoundedTransformationBuilder;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by issuser on 2018/4/11.
 */

public class MessageTypeAdapter extends QuickAdapter<MessageBean> {
    public MessageTypeAdapter(Context context) {
        super(context, R.layout.item_message);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MessageBean item, int position) {
        ImageView item_icon=helper.getView(R.id.item_icon);
        TextView item_title=helper.getView(R.id.item_title);
        ImageView item_mark=helper.getView(R.id.item_mark);
        ImageView content_pic=helper.getView(R.id.content_pic);

        Glide.with(context).load("http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg")
                .centerCrop()
                .override(320,160)
                .transform(new CenterCrop(context), new RoundedTransformationBuilder().cornerRadius(20).build(context))
                .into(content_pic);
    }
}
