package com.isoftston.issuser.conchapp.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.PopListAdapter;

import java.util.List;

/**
 * Created by john on 2018/4/15.
 */

public class ChooseListPopupWindow extends PopupWindow{

    private PopListAdapter adapter;

    public ChooseListPopupWindow(Context context){
        init(context);
    }

    public void init(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.pop_choose_list,null);
        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(null, (Bitmap) null));
        setAnimationStyle(R.style.anim_style);

        ListView listView=view.findViewById(R.id.lv);
        TextView tv_cancle=view.findViewById(R.id.tv_cancel);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter=new PopListAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    listener.choice(adapter.getItem(position));
                }
                dismiss();
            }
        });
    }
    public void showAtBottom(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    public void setDatas(List<String> list){
        adapter.replaceAll(list);
        adapter.notifyDataSetChanged();
    }

    private PopOnItemClick listener;

    public void setPopOnItemClickListener(PopOnItemClick listener){
        this.listener=listener;
    }

    public interface PopOnItemClick{
        void choice(String content);
    }
}
