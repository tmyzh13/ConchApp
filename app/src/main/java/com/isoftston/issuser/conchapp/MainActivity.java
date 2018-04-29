package com.isoftston.issuser.conchapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.views.tab.InterceptedFragmentTabHost;
import com.corelibs.views.tab.TabChangeInterceptor;
import com.corelibs.views.tab.TabNavigator;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.presenter.LoginPresenter;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.check.CheckFragment;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;
import com.isoftston.issuser.conchapp.views.message.MessageFragment;
import com.isoftston.issuser.conchapp.views.mine.MineFragment;
import com.isoftston.issuser.conchapp.views.security.SecurityFragment;
import com.isoftston.issuser.conchapp.views.work.WorkFragment;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

import butterknife.Bind;

public class MainActivity extends BaseActivity<LoginView,LoginPresenter> implements TabNavigator.TabNavigatorContent,LoginView {

    @Bind(android.R.id.tabhost)
    InterceptedFragmentTabHost tabHost;

    private TabNavigator navigator = new TabNavigator();
    private String[] tabTags;
    private Context context=MainActivity.this;
    private int bgRecourse[] = new int[]{
            R.drawable.tab_msg,
            R.drawable.tab_trouble,
            R.drawable.tab_work,
            R.drawable.tab_check,
            R.drawable.tab_mine
    };

    public static Intent getLauncher(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        return intent;
    }


    @Override
    protected int getLayoutId() {
        if(TextUtils.isEmpty(PreferencesHelper.getData(Constant.LOGIN_STATUE))){
            startActivity(LoginActivity.getLauncher(context));
        }
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
            tabTags = new String[]{getString(R.string.home_message), getString(R.string.home_security),getString(R.string.home_work),
                    getString(R.string.home_check),getString(R.string.home_mine)};
            navigator.setup(this, tabHost, this, getSupportFragmentManager(), R.id.real_tab_content);
        navigator.setTabChangeInterceptor(new TabChangeInterceptor() {
            @Override
            public boolean canTab(String tabId) {
                if(tabId.equals(tabTags[3])) {
                    setBarColor(getResources().getColor(R.color.transparent_black));
                }else{
                    setBarColor(getResources().getColor(R.color.transparent));
                }
                return true;
            }

            @Override
            public void onTabIntercepted(String tabId) {
            }
        });

        //presenter = createPresenter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter!=null){
            presenter.getPushTag();
        }

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public View getTabView(int position) {
        View view = getLayoutInflater().inflate(R.layout.view_tab_content, null);

        ImageView icon = (ImageView) view.findViewById(R.id.iv_tab_icon);
        TextView text = (TextView) view.findViewById(R.id.tv_tab_text);
        TextView tv_msg_count=view.findViewById(R.id.tv_msg_count);
        if(position!=0){
            tv_msg_count.setVisibility(View.GONE);
        }
        icon.setImageResource(bgRecourse[position]);
        text.setText(tabTags[position]);
        return view;
    }

    @Override
    public Bundle getArgs(int position) {
        return null;
    }

    @Override
    public Class[] getFragmentClasses() {
        return new Class[]{MessageFragment.class, SecurityFragment.class, WorkFragment.class,CheckFragment.class, MineFragment.class};
    }

    @Override
    public String[] getTabTags() {
        return tabTags;
    }

    /**
     * 物理返回键拦截
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doublePressBackToast();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 双击两次返回键退出应用
     */
    private boolean isBackPressed = false;//判断是否已经点击过一次回退键

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            showToast("再次点击返回退出程序");
        } else {
            AppManager.getAppManager().finishAllActivity();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }

    @Override
    public void loginSuccess(String data) {

    }

    @Override
    public void changePwdSuccess() {

    }

    @Override
    public void getCodeSuccess() {

    }

    @Override
    public void returnTag(boolean isSuccess,String tag) {
        if(isSuccess){
            addTag(tag);
        }else{
            deleteTag(tag);
        }
    }


    private void addTag(String username) {
        //final String tag = "2c9af58150f5a3e80150f5c6d51e000b";
        final String tag = username;

        if (TextUtils.isEmpty(tag)) {
            //Toast.makeText(this, "请先输入tag", Toast.LENGTH_SHORT).show();
            return;
        }
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

                Log.i("isSuccess", String.valueOf(isSuccess));
            /*handler.post(new Runnable() {
                @Override
                public void run() {
                    inputTag.setText("");
                    if (isSuccess) {
                        sharedPref.edit().putInt(TAG_REMAIN, result.remain).apply();
                        tagRemain.setText(String.valueOf(result.remain));
                        PushDialogFragment.newInstance(0, 1, getString(R.string.push_add_success), tag).show(
                                getFragmentManager(), "addTag");
                    } else {
                        PushDialogFragment.newInstance(0, 0, getString(R.string.push_add_fail), tag).show(
                                getFragmentManager(), "addTag");
                    }
                }
            });*/
            }
        }, tag);
    }


    private void deleteTag(String tag) {
        // final String tag = inputTag.getText().toString();
        if (TextUtils.isEmpty(tag)) {
            //Toast.makeText(this, "请先输入tag", Toast.LENGTH_SHORT).show();
            return;
        }
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
             /*   handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputTag.setText("");
                        if (isSuccess) {
                            sharedPref.edit().putInt(TAG_REMAIN, result.remain).apply();
                            tagRemain.setText(String.valueOf(result.remain));
                            PushDialogFragment.newInstance(0, 1,
                                    getString(R.string.push_delete_success), tag).show(getFragmentManager(), "deleteTag");
                        } else {
                            PushDialogFragment.newInstance(0, 0,
                                    getString(R.string.push_delete_fail), tag).show(getFragmentManager(), "deleteTag");
                        }
                    }
                });*/
            }
        }, tag);
    }

}
