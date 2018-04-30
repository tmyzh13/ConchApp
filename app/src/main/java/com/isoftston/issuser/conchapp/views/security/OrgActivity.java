package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.NodeTreeAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.utils.Node;
import com.isoftston.issuser.conchapp.utils.NodeHelper;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/26.
 */

public class OrgActivity extends BaseActivity<SecuryView,SecurityPresenter> implements SecuryView  {
    private Context context = OrgActivity.this;
    private NodeTreeAdapter mAdapter;

    private LinkedList<Node> mLinkedList = new LinkedList<>();
    private List<Node> data = new ArrayList<>();

    private static final String TAG = OrgActivity.class.getSimpleName();

    private final Integer FIND_RESULT_CODE = 130;

    private final Integer HIDDEN_RESULT_CODE = 131;

    private int code;

    private int pos;

    //记录已请求的记录
    private List<Integer> posList = new ArrayList<>();

    public static Intent getLaucnher(Context context,int i){//根据code设置头部标题文字
        Intent intent =new Intent(context,OrgActivity.class);
        intent.putExtra("code",i);
        return intent;
    }

    @Bind(R.id.id_tree)
    ListView mListView;

    @Bind(R.id.nav)
    NavBar nav;

    @Override
    protected int getLayoutId() {
        return R.layout.org_select_dept_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        code = getIntent().getIntExtra("code",-1);
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);
        if(0 == code)
        {
            nav.setNavTitle(getString(R.string.find_company));
        }
        else if(1 == code)
        {
            nav.setNavTitle(getString(R.string.trouble_company));
        }

        mAdapter = new NodeTreeAdapter(context,mListView,mLinkedList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Node node = (Node)mAdapter.getItem(position);
                //Toast.makeText(getApplicationContext(),"选中2:"+ node.get_label(),Toast.LENGTH_SHORT).show();
                Log.i(TAG,"node pos:" + position + ",node name:" + node.get_label() + ",node id:" + node.get_id() + ",node child:" + node.hasChild());

                //下一级请求
                if(node.hasChild())
                {
                    pos = position;
                    if(!posList.contains(pos))
                    {
                        presenter.getCompanyChoiceNextList((String)node.get_id());
                        posList.add(pos);
                    }
                    else
                    {
                        mAdapter.expandOrCollapse(pos);
                    }
                }
                else
                {
                    //选中单位
                    Intent intent =new Intent();
                    if(0 == code)
                    {
                        intent.putExtra(Constant.FIND_COMPANY_NAME,node.get_label());
                        intent.putExtra(Constant.FIND_COMPANY_ID,(String)node.get_id());
                        setResult(FIND_RESULT_CODE,intent);
                    }
                    else if(1 == code)
                    {
                        intent.putExtra(Constant.DANGER_COMPANY_NAME,node.get_label());
                        intent.putExtra(Constant.DANGER_COMPANY_ID,(String)node.get_id());
                        setResult(HIDDEN_RESULT_CODE,intent);
                    }
                    finish();
                }
            }
        });

        presenter.getCompanyChoiceList();

    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void addSuccess() {

    }

    @Override
    public void getSafeListSuccess(SafeListBean data) {

    }

    @Override
    public void addFailed() {

    }

    @Override
    public void getSafeChoiceList(SecuritySearchBean bean) {
        data.addAll(bean.ORG);
        mLinkedList.addAll(NodeHelper.sortNodes(data));
        mLinkedList = NodeHelper.sortLinkedNodes(mLinkedList);
        data.clear();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void getOrgList(List<OrgBean> bean) {
        data.addAll(bean);
        mLinkedList.addAll(NodeHelper.sortNodes(data));
        mLinkedList = NodeHelper.sortLinkedNodes(mLinkedList);
        mAdapter.notifyDataSetChanged();
        mAdapter.expandOrCollapse(pos);
    }

    @Override
    public void getOrgId(String orgId) {

    }

}
