package com.isoftston.issuser.conchapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.isoftston.issuser.conchapp.views.security.TypeMessageFragment;

/**
 * Created by issuser on 2018/4/10.
 */

public class MessageTypePageAdapter extends FragmentPagerAdapter{

    private String[] mTitles;
    private int bigType;
    public MessageTypePageAdapter(FragmentManager fm, String[] mTitles, int i){
        super(fm);
        this.mTitles=mTitles;
        this.bigType=i;
    }

    private final String TAG = MessageTypePageAdapter.class.getSimpleName();

    public void setmTitles(String[] mTitles){
        this.mTitles=mTitles;
    }

    @Override
    public Fragment getItem(int position) {

        //"全部 12->全部"
        String fullTxt = mTitles[position];

        Log.i(TAG,"pos:" + position + ",txt:" + fullTxt);

        int index = fullTxt.indexOf(' ');
        if(-1 != index)
        {
            fullTxt = fullTxt.substring(0,index);
        }
        return TypeMessageFragment.newInstance(fullTxt,bigType);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
