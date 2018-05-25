package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.corelibs.base.BaseActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.NodeTreeAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.CompanyBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.CompanySelectPresenter;
import com.isoftston.issuser.conchapp.utils.Node;
import com.isoftston.issuser.conchapp.utils.NodeHelper;
import com.isoftston.issuser.conchapp.views.interfaces.CompanySelectView;
import com.isoftston.issuser.conchapp.views.security.OrgActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

public class CompanySelectActivity extends BaseActivity <CompanySelectView,CompanySelectPresenter> implements CompanySelectView {


    private Context context = CompanySelectActivity.this;
    private NodeTreeAdapter mAdapter;

    private LinkedList<Node> mLinkedList = new LinkedList<>();
    private List<Node> data = new ArrayList<>();

    private static final String TAG = OrgActivity.class.getSimpleName();

    private int pos;

    private String id;
    private final int RESULT_CODE = 10;
    private boolean isExpand;

    //记录已请求的记录
    private List<Integer> posList = new ArrayList<>();

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
        id = getIntent().getStringExtra("id");
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);
        nav.setNavTitle(getString(R.string.user_company));

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
                        presenter.getCompanyChoiceList((String)node.get_id());
                        posList.add(pos);
                        isExpand = true;
                    }
                    else
                    {
                        mAdapter.expandOrCollapse(pos);
                    }
                }
                else
                {
                    //选中单位
                    UserInfoBean bean = new UserInfoBean();
                    bean.setFactoryId((String) node.get_id());
                    bean.setCompanyName(node.get_label());
                    presenter.updateUserCompany(bean);
                    Intent intent =new Intent();
                    intent.putExtra(Constant.FIND_COMPANY_NAME,node.get_label());
                    intent.putExtra(Constant.FIND_COMPANY_ID,(String)node.get_id());
                    setResult(RESULT_CODE,intent);
                }
            }
        });

        presenter.getCompanyChoiceList(id);
    }

    @Override
    protected CompanySelectPresenter createPresenter() {
        return new CompanySelectPresenter();
    }

    public static Intent getLaucner(Context context,String id) {
        Intent intent =new Intent(context,CompanySelectActivity.class);
        intent.putExtra("id",id);
        return intent;
    }



    @Override
    public void getOrgList(List<CompanyBean> bean) {
        data.addAll(bean);
        mLinkedList.addAll(NodeHelper.sortNodes(data));
        mLinkedList = NodeHelper.sortLinkedNodes(mLinkedList);
        mAdapter.notifyDataSetChanged();
        if (isExpand){
            mAdapter.expandOrCollapse(pos);
        }
    }

    @Override
    public void submit() {
        finish();
    }
}
