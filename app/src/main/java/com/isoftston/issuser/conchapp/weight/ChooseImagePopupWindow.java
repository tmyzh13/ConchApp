package com.isoftston.issuser.conchapp.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;

/**
 * Created by issuser on 2018/4/12.
 */

public class ChooseImagePopupWindow extends PopupWindow {

    private OnTypeChosenListener listener;

    private View.OnClickListener dismiss = new View.OnClickListener() {
        @Override public void onClick(View v) {
            dismiss();
        }
    };

    public ChooseImagePopupWindow(Context context,int type) {
        init(context,type);
    }

    private void init(Context context,int type) {//0:拍照、相册， 1：拍照， 2：相册
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_image, null);

        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(null, (Bitmap) null));
        setAnimationStyle(R.style.anim_style);


        LinearLayout ll_photo=view.findViewById(R.id.ll_photo);
        LinearLayout ll_gallery=view.findViewById(R.id.ll_gallery);
        LinearLayout gallery_ll = view.findViewById(R.id.gallery_ll);
        if (type == 1){//只显示拍照
            gallery_ll.setVisibility(View.GONE);
        }
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);

        cancel.setOnClickListener(dismiss);
        view.setOnClickListener(dismiss);

        ll_photo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null) listener.onCamera();
                dismiss();
            }
        });

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null) listener.onGallery();
                dismiss();
            }
        });
    }

    public void showAtBottom(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void setOnTypeChosenListener(OnTypeChosenListener l) {
        listener = l;
    }

    public interface OnTypeChosenListener {
        void onCamera();
        void onGallery();
    }
}
