package com.isoftston.issuser.conchapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.isoftston.issuser.conchapp.views.security.HiddenTroubleMessageFragment;
import com.isoftston.issuser.conchapp.views.security.IllegalMessageFragment;
import com.isoftston.issuser.conchapp.views.security.MineMsgFragment;

/**
 * Created by issuser on 2018/4/12.
 */

public class IllegalTypeAdapter  extends FragmentStatePagerAdapter {

    private HiddenTroubleMessageFragment hiddenTroubleMessageFragment;
    private IllegalMessageFragment illegalMessageFragment;
    private MineMsgFragment mineMsgFragment;

    public IllegalTypeAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0){
            if(hiddenTroubleMessageFragment==null){
                Log.e("yzh","hiddenTouble");
                hiddenTroubleMessageFragment=new HiddenTroubleMessageFragment();
            }
            return hiddenTroubleMessageFragment;
        }else if(position==1){
            if(illegalMessageFragment==null){
                Log.e("yzh","illegalMessage");
                illegalMessageFragment=new IllegalMessageFragment();
            }
            return illegalMessageFragment;
        }else if(position==2){
            if(mineMsgFragment==null){
                Log.e("yzh","mineMsg");
                mineMsgFragment=new MineMsgFragment();
            }
            return mineMsgFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
