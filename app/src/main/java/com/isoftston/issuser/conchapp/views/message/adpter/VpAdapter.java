package com.isoftston.issuser.conchapp.views.message.adpter;

/**
 * Created by issuser on 2018/3/5.
 */

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import java.util.List;


public class VpAdapter extends PagerAdapter {

    private List<View> list;
    private Handler handler;
    public VpAdapter(List<View> list, Handler handler) {
        this.list = list;
        this.handler = handler;
    }
    @Override
    public int getCount() {
        return list.size() ;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = list.get(position);

//        //点击图片的时停止轮播
//        view.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_CANCEL://鼠标划走
//                        //发送信息
//                        handler.sendEmptyMessageDelayed(1, 2000);
//                        break;
//                    case MotionEvent.ACTION_DOWN://鼠标按下
//                        //清空所有handler消息池的信息及所有毁掉函数
//                        handler.removeCallbacksAndMessages(null);
//                        break;
//                    case MotionEvent.ACTION_UP://鼠标抬起
//                        //发送信息
//                        handler.sendEmptyMessageDelayed(1, 2000);
//                        break;
//                }
//                return true;
//            }
//        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}
