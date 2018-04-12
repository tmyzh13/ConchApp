package com.isoftston.issuser.conchapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.isoftston.issuser.conchapp.views.security.TypeMessageFragment;

/**
 * Created by issuser on 2018/4/10.
 */

public class MessageTypePageAdapter extends FragmentPagerAdapter{

    private String[] mTitles;

    public MessageTypePageAdapter(FragmentManager fm,String[] mTitles){
        super(fm);
        this.mTitles=mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return TypeMessageFragment.newInstance(mTitles[position]);
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
