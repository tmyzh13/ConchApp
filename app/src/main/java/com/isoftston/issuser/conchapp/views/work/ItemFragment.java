package com.isoftston.issuser.conchapp.views.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.security.TypeMessageFragment;
import com.isoftston.issuser.conchapp.views.work.adpter.ListviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class ItemFragment extends BaseFragment {
    private List<String> list = new ArrayList<>();
    @Bind(R.id.listview)
    ListView mlistview;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //获取Activity传递过来的参数
        Bundle mBundle = getArguments();
        String title = mBundle.getString("arg");
        Log.i("ZT",title);
        ToastUtils.showtoast(getActivity(), title);
        initData();
        ListviewAdapter listviewAdapter = new ListviewAdapter(getActivity(), list);
        mlistview.setAdapter(listviewAdapter);

        //进入现场作业信息点
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ScanCodeActivity.getLauncher(getContext()));
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            list.add("我这是假数据假数据假数据" + i + "itme");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
