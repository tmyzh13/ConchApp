package com.isoftston.issuser.conchapp.views.work.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.isoftston.issuser.conchapp.views.work.ItemFragment;

import java.util.List;

/**
 * Created by issuser on 2018/4/16.
 */

public class WorkMessageAdapter  extends FragmentPagerAdapter {
    private List<String> list;
    private int bigType;

    public WorkMessageAdapter(FragmentManager fm, List<String> list, int i){
        super(fm);
        this.list=list;
        this.bigType=i;
    }

    public void setmTitles(List<String> list){
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance(list.get(position),bigType);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
