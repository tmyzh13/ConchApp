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

import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.views.work.adpter.ListviewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issuser on 2018/4/9.
 */

public class ItemFragment extends Fragment {
    private List<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
        ListView mlistview = (ListView) contextView.findViewById(R.id.listview);

        //获取Activity传递过来的参数
        Bundle mBundle = getArguments();
        String title = mBundle.getString("arg");
        Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
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
        return contextView;
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
