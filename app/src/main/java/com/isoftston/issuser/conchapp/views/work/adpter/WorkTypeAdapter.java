package com.isoftston.issuser.conchapp.views.work.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.isoftston.issuser.conchapp.views.work.CommonMessageFragment;
import com.isoftston.issuser.conchapp.views.work.DangerMessageFragment;
import com.isoftston.issuser.conchapp.views.work.MineMessageFragment;


/**
 * Created by issuser on 2018/4/16.
 */

public class WorkTypeAdapter extends FragmentPagerAdapter {

    private DangerMessageFragment dangerMessageFragment;
    private CommonMessageFragment commonMessageFragment;
    private MineMessageFragment mineMessageFragment;

    public WorkTypeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            if(dangerMessageFragment==null){
                dangerMessageFragment=new DangerMessageFragment();
            }
            return dangerMessageFragment;
        }else if(position==1){
            if(commonMessageFragment==null){
                commonMessageFragment=new CommonMessageFragment();
            }
            return commonMessageFragment;
        }else if(position==2){
            if(mineMessageFragment==null){
                mineMessageFragment=new MineMessageFragment();
            }
            return mineMessageFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
