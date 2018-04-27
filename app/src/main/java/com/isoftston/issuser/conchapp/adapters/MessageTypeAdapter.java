package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.corelibs.views.roundedimageview.RoundedTransformationBuilder;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by issuser on 2018/4/11.
 */

public class MessageTypeAdapter extends QuickAdapter<MessageBean> {

    private SimpleDateFormat time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public MessageTypeAdapter(Context context) {
        super(context, R.layout.item_message);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, MessageBean item, int position) {
        ImageView item_icon=helper.getView(R.id.item_icon);
        ImageView item_mark=helper.getView(R.id.item_mark);
        ImageView content_pic=helper.getView(R.id.content_pic);

        if ("wz".equals(item.getType())){
            item_icon.setImageResource(R.mipmap.illegal_icon);
            helper.setText(R.id.item_title,context.getString(R.string.wz_message))
                    .setText(R.id.address,item.getLocation())
                    .setText(R.id.content,item.getContent());
            if (!"".equals(item.getCreateTime())){
                helper.setText(R.id.item_time,time.format(new Date(Long.valueOf(item.getCreateTime()))));
            }
        }else if ("aq".equals(item.getType())){
            item_icon.setImageResource(R.mipmap.aq_icon);
            helper.setText(R.id.item_title,context.getString(R.string.aq_message))
                    .setText(R.id.address,item.getLocation())
                    .setText(R.id.content,item.getContent());
            if (!"".equals(item.getCreateTime())){
                helper.setText(R.id.item_time,time.format(new Date(Long.valueOf(item.getCreateTime()))));
            }
        }else {
            item_icon.setImageResource(R.mipmap.yh_icon);
            helper.setText(R.id.item_title,context.getString(R.string.yh_message))
                    .setText(R.id.address,item.getLocation())
                    .setText(R.id.content,item.getContent());
            if (!"".equals(item.getCreateTime())){
                helper.setText(R.id.item_time,time.format(new Date(Long.valueOf(item.getCreateTime()))));
            }
        }
        if (item.getYhjb() != null && ("ZDYH").equals(item.getYhjb())){
            item_mark.setVisibility(View.VISIBLE);
        }else if (item_mark.getVisibility() == View.VISIBLE){
            item_mark.setVisibility(View.GONE);
        }
        if (item.getImgs() != null){
            content_pic.setVisibility(View.VISIBLE);
            String path[] = item.getImgs().split(",");
            Log.d("path","path="+path[0]);
            Glide.with(context).load(Urls.ROOT + path[0])
                    .centerCrop()
                    .override(320,160)
                    .transform(new CenterCrop(context), new RoundedTransformationBuilder().cornerRadius(20).build(context))
                    .into(content_pic);
        }else{
            content_pic.setVisibility(View.GONE);
        }
    }
}
