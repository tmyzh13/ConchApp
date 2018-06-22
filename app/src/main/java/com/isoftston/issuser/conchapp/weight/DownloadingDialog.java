package com.isoftston.issuser.conchapp.weight;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.isoftston.issuser.conchapp.R;

import cn.bingoogolapple.progressbar.BGAProgressBar;

public class DownloadingDialog extends AppCompatDialog {

    private BGAProgressBar mProgressBar;

    public DownloadingDialog(Context context) {
        super(context, R.style.AppDialogTheme);
        setContentView(R.layout.dialog_downloading);
        mProgressBar = (BGAProgressBar) findViewById(R.id.pb_downloading_content);
        setCancelable(false);
    }

    public void setProgress(long progress, long maxProgress) {
        mProgressBar.setMax((int) maxProgress);
        mProgressBar.setProgress((int) progress);
    }

    @Override
    public void show() {
        super.show();
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }
}
