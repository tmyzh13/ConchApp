package com.isoftston.issuser.conchapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Urls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class UpdateManager {

    private final String TAG = UpdateManager.class.getSimpleName();

    private Context mContext; //上下文

    private String apkUrl = Urls.ROOT + Urls.UPDATE_URL; //apk下载地址
    private static final String savePath = "/sdcard/updateAPK/"; //apk保存到SD卡的路径
    private static final String saveFileName = savePath + "apkName.apk"; //完整路径名

    private ProgressBar mProgress; //下载进度条控件
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progress; //下载进度
    private boolean cancelFlag = false; //取消下载标志位

    private String updateDescription = null;   //更新内容描述信息

    private AlertDialog alertDialog1, alertDialog2; //表示提示对话框、进度条对话框

    /** 构造函数 */
    public UpdateManager(Context context) {
        this.mContext = context;
        updateDescription = mContext.getString(R.string.update_desc);
    }

    /** 显示更新对话框 */
    public void showNoticeDialog(String serverVersion) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(mContext.getString(R.string.find_new) + serverVersion);
        dialog.setMessage(updateDescription);
        dialog.setPositiveButton(mContext.getString(R.string.now_update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                showDownloadDialog();
            }
        });

        alertDialog1  = dialog.create();
        alertDialog1.setCancelable(false);
        alertDialog1.show();
    }

    /** 显示进度条对话框 */
    public void showDownloadDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(mContext.getString(R.string.now_update));
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        dialog.setView(v);

        alertDialog2  = dialog.create();
        alertDialog2.setCancelable(false);
        alertDialog2.show();

        //下载apk
        downloadAPK();
    }

    /** 下载apk的线程 */
    public void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if(!file.exists()){
                        file.mkdir();
                    }
                    String apkFile = saveFileName;
                    File ApkFile = new File(apkFile);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do{
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int)(((float)count / length) * 100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWNLOADING);
                        if(numread <= 0){
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWNLOADED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    }while(!cancelFlag); //点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch(Exception e) {
                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /** 更新UI的handler */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOADING:
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOADED:
                    if (alertDialog2 != null)
                        alertDialog2.dismiss();
                    installAPK();
                    break;
                case DOWNLOAD_FAILED:
                    Toast.makeText(mContext,mContext.getString(R.string.connect_error) , Toast.LENGTH_LONG).show();
                    if (alertDialog2 != null)
                        alertDialog2.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    /** 下载完成后自动安装apk */
    public void installAPK() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}