package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.corelibs.views.roundedimageview.RoundedTransformationBuilder;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.SecurityTroubleBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bbwug on 2018/4/27.
 */

public class SecurityAdapter extends QuickAdapter<SecurityTroubleBean> {

    private SimpleDateFormat time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    public SecurityAdapter(Context context) {
        super(context, R.layout.item_message);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, SecurityTroubleBean item, int position) {
        ImageView item_icon = helper.getView(R.id.item_icon);
//        TextView item_title=helper.getView(R.id.item_title);
//        ImageView item_mark=helper.getView(R.id.item_mark);
//        String time= DateUtils.getDateToString(item.getCreateTime());
        ImageView content_pic = helper.getView(R.id.content_pic);
        View readStatus= helper.getView(R.id.view_read_statue);
//        .setText(R.id.item_time,DateUtils.getDateToString(item.getCreateTime()));
//                .setImageUrl(R)

        if ("ZYZYWZBD".equals(item.getYhlx())||"QT".equals(item.getYhlx())|| "YHSW".equals(item.getYhlx())||"WCZWZZY".equals(item.getYhlx())||"ZHSWWZZH".equals(item.getYhlx())||"GRFHZBBQ".equals(item.getYhlx())) {
            item_icon.setImageResource(R.mipmap.yh_icon);
            helper.setText(R.id.item_title, context.getString(R.string.wz_message))
                    .setText(R.id.address, item.getYhdd())
                    .setText(R.id.content, item.getYhms());
            if (!"".equals(item.getCjsj())) {
                helper.setText(R.id.item_time, time.format(new Date(Long.valueOf(item.getCjsj()))));
            }
//        } else if ("aq".equals(item.getType())) {
//            item_icon.setImageResource(R.mipmap.aq_icon);
//            helper.setText(R.id.item_title, context.getString(R.string.aq_message))
//                    .setText(R.id.address, item.getYhdd())
//                    .setText(R.id.content, item.getYhms());
//            if (!"".equals(item.getCjsj())) {
//                helper.setText(R.id.item_time, time.format(new Date(Long.valueOf(item.getCjsj()))));
//            }
        } else {
            item_icon.setImageResource(R.mipmap.yh_icon);
            helper.setText(R.id.item_title, context.getString(R.string.yh_message))
                    .setText(R.id.address, item.getYhdd())
                    .setText(R.id.content, item.getYhms());
            if (!"".equals(item.getCjsj())) {
                helper.setText(R.id.item_time, time.format(new Date(Long.valueOf(item.getCjsj()))));
            }
        }
        if(!item.isRead()){ //已读未读控制
            readStatus.setVisibility(View.VISIBLE);
        }else{
            readStatus.setVisibility(View.GONE);
        }
        if (item.getTplj() != null) {
            String path[] = item.getTplj().split(",");
            Log.d("path", "path=" + path[0]);
            Glide.with(context).load(Urls.ROOT + path[0])
                    .centerCrop()
                    .override(320, 160)
                    .transform(new CenterCrop(context), new RoundedTransformationBuilder().cornerRadius(20).build(context))
                    .into(content_pic);
        }
    }
}
