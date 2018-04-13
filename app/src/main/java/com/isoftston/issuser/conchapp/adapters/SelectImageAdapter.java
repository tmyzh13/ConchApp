package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.ChosenImageFile;
import com.isoftston.issuser.conchapp.utils.Tools;

import java.util.List;


/**
 * Created by yizh on 2016/6/15.
 */
public class SelectImageAdapter extends QuickAdapter<ChosenImageFile>
        implements View.OnClickListener{

    private int maxSize;
    private boolean isBig=false;

    public SelectImageAdapter(Context context, int layoutResId, int maxSize) {
        super(context, layoutResId);
        this.maxSize = maxSize;
    }
    public SelectImageAdapter(Context context,int layoutResId,int maxSize,boolean b){
        super(context, layoutResId);
        this.maxSize = maxSize;
        this.isBig=b;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ChosenImageFile item, final int position) {
        helper.setVisible(R.id.image, item.chosen)

                .setVisible(R.id.iv_plus, !item.chosen)
               ;
        ImageView image=helper.getView(R.id.image);
        ImageView iv_plus=helper.getView(R.id.iv_plus);

        int width;
           width = (Tools.getScreenWidth(context)- Tools.dip2px(context,60))/3;
            iv_plus.setImageResource(R.mipmap.plus);
            ViewGroup.LayoutParams  ps = image.getLayoutParams();
            ps.height=width;
            ViewGroup.LayoutParams ps1=iv_plus.getLayoutParams();
            ps1.height=width;



        if (item.chosen) {
            DrawableRequestBuilder builder;
            if(item.fromSet){
                builder= Glide.with(context).load(item.imageUrl)
                        .override(320,320).centerCrop();
            }else{
                builder= Glide.with(context).load(item.image)
                        .override(320,320).centerCrop();
            }
            helper.setImageBuilder(R.id.image,builder);
        }
//            RequestCreator creator = Picasso.with(context).load(item.image)
//                    .config(Bitmap.Config.RGB_565).resize(720, 720).centerCrop()
//                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
//            helper.setImageBuilder(R.id.image, creator);
//        }
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (getChosenCount() == maxSize){
                    add(ChosenImageFile.emptyInstance());
                }
                remove(position);
                return false;
            }
        });
    }

    public int getChosenCount() {
        int count = 0;
        for (ChosenImageFile file : data) {
            if (file.chosen) count++;
        }

        return count;
    }

    @Override
    public void onClick(View v) {

    }

    public List<ChosenImageFile> getData() {
        return data;
    }



}
