package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.FileCacheUtils;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.settings_push_msg_switch)
    Switch mPushMsgSwitch;
    @Bind(R.id.clean_cache_view)
    RelativeLayout mCleanCacheView;
    @Bind(R.id.settings_cleanup_cache_title)
    TextView mCacheSizeTitleView;
    @Bind(R.id.settings_cleanup_cache)
    TextView mCacheSizeView;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.settings));
//        boolean receive = SharedPreferencesUtils.getReceiveNotice(this);
//        mPushMsgSwitch.setChecked(receive);
        File outCachePath = getApplicationContext().getExternalCacheDir();
        String outCacheSize = null;
        try {
            outCacheSize = FileCacheUtils.getCacheSize(outCachePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCacheSizeView.setText(outCacheSize);
        mPushMsgSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //接收推送
//                    SharedPreferencesUtils.setReceiveNotice(SettingsActivity.this,true);
                } else {
                    //不接收推送
//                    SharedPreferencesUtils.setReceiveNotice(SettingsActivity.this,false);
                }
            }
        });
    }

    @OnClick(R.id.clean_cache_view)
    public void cleanCache() {
        //清除缓存
        FileCacheUtils.cleanExternalCache(getApplicationContext());
        //重新获取一次缓存大小，自处理M，byte
        initCacheSize();

    }

    private void initCacheSize() {
    /*
    * 获取SD卡根目录：Environment.getExternalStorageDirectory().getAbsolutePath();
        外部Cache路径：/mnt/sdcard/android/data/com.xxx.xxx/cache 一般存储缓存数据（注：通过getExternalCacheDir()获取）
        外部File路径：/mnt/sdcard/android/data/com.xxx.xxx/files 存储长时间存在的数据
        （注：通过getExternalFilesDir(String type)获取， type为特定类型，可以是以下任何一种
                    Environment.DIRECTORY_MUSIC,
                    Environment.DIRECTORY_PODCASTS,
                     Environment.DIRECTORY_RINGTONES,
                     Environment.DIRECTORY_ALARMS,
                     Environment.DIRECTORY_NOTIFICATIONS,
                     Environment.DIRECTORY_PICTURES,
                      Environment.DIRECTORY_MOVIES. ）
    * */
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File outCachePath = getApplicationContext().getExternalCacheDir();
        File outFilePath = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS);

        try {
            String outCacheSize = FileCacheUtils.getCacheSize(outCachePath);
            String outFileSize = FileCacheUtils.getCacheSize(outFilePath);

            mCacheSizeView.setText(outCacheSize);

            if (outCacheSize.matches("0.00B")) {
                mCleanCacheView.setEnabled(false);
                mCacheSizeTitleView.setTextColor(getResources().getColor(R.color.hit_text_color));
                mCacheSizeView.setTextColor(getResources().getColor(R.color.hit_text_color));
            } else {
                mCleanCacheView.setEnabled(true);
                mCacheSizeTitleView.setTextColor(getResources().getColor(R.color.text_color_main));
                mCacheSizeView.setTextColor(getResources().getColor(R.color.single_text_color));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
