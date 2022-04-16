package com.benben.wordtutor.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.benben.wordtutor.R;
import com.benben.wordtutor.bean.ResponseBean;
import com.benben.wordtutor.listener.IDialogBtnClickCallBack;

import com.benben.wordtutor.permission.ICheckPermissionCallBack;
import com.benben.wordtutor.permission.PermissionUtils;
import com.benben.wordtutor.utils.Api;
import com.benben.wordtutor.utils.AuthUtils;
import com.benben.wordtutor.utils.DialogUtils;
import com.benben.wordtutor.utils.Utils;
import com.orhanobut.hawk.Hawk;


import java.io.File;
import java.util.Arrays;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private static final int GO_MAIN = 0x10;
    private static final int CHECK_UPDATE = 0x11;
    private static final int REQUEST_PERMISSION = 0x12;
    private boolean isRequestPermission = false;//默认未请求权限
    private boolean isGoSettingPage = false;//是否进入过setting 页面

    private static final String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
    };


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_UPDATE:
                    //检查更新
                    checkAppUpdate();
                    break;
                case GO_MAIN:
                    //进入主界面
                    goMain();
                    break;
            }
        }
    };


    /**
     * 检查更新
     */
    private void checkAppUpdate() {
        Log.d(TAG, "checkAppUpdate: 检查app更新...");
        mHandler.sendEmptyMessage(GO_MAIN);

    }



    public  void installApk(Context context, File file) throws Exception{
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行的数据类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

        finish();
    }

    /**
     * 去主页
     */
    private void goMain() {
        Log.d(TAG, "goMain: 加载主页...");
        Intent intent = new Intent();
        Boolean isFirst = Hawk.get("is_first", true);
        if(isFirst){
            Hawk.put("is_first", false);
            intent.setClass(this, GuideActivity.class);
            startActivity(intent);
        }else{

            if(!AuthUtils.hasLogin()){
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
            }else{
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
            }

        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: SplashActivity 显示了...");
        if (!isRequestPermission) {
            isRequestPermission = true;
            requestPermission();
        }

        //如果进入过设置页面 回来之后要重新检查更新 以便于顺利执行其他的启动工作
        if (isGoSettingPage) {
            isGoSettingPage = false;
            mHandler.sendEmptyMessage(CHECK_UPDATE);
        }

    }

    @Override
    protected void initView() {
        fullScreen(this, false);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    }

    private void requestPermission() {

        PermissionUtils.getInstance().getPermissionManager().checkPermissions(this, permissions, new ICheckPermissionCallBack() {
            @Override
            public void onPermissionGranted() {
                mHandler.sendEmptyMessage(CHECK_UPDATE);
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                if (deniedPermissions != null && deniedPermissions.length > 0) {
                    Log.d(TAG, "onPermissionDenied: 未授权的权限列表 == " + Arrays.asList(deniedPermissions));
                    PermissionUtils.getInstance().getPermissionManager().requestPermissions(SplashActivity.this, deniedPermissions, REQUEST_PERMISSION);
                } else {
                    mHandler.sendEmptyMessage(CHECK_UPDATE);
                }

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults != null && grantResults.length > 0) {
                boolean isAllGranted = true;
                for (int i = 0; i < grantResults.length; i++) {
                    int result = grantResults[i];
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false;
                        break;
                    }
                }

                if (isAllGranted) {
                    //全部授权 检查升级
                    mHandler.sendEmptyMessage(CHECK_UPDATE);
                } else {

                    //弹窗提示用户手动授权权限
                    DialogUtils.showDialog(this, getString(R.string.授权提示), getString(R.string.权限提示语), getString(R.string.去开启), getString(R.string.取消), new IDialogBtnClickCallBack() {
                        @Override
                        public void onPositiveButtonClicked() {
                            Log.d(TAG, "onPositiveButtonClicked: 去设置");
                            isGoSettingPage = true;
                            try {
                                PermissionUtils.getInstance().getPermissionManager().requestManualySetPerm(SplashActivity.this);
                            } catch (Exception e) {
                                e.printStackTrace();
                                mHandler.sendEmptyMessage(CHECK_UPDATE);
                            }

                        }

                        @Override
                        public void onNegativeButtonClicked() {
                            Log.d(TAG, "onNegativeButtonClicked: 取消了...");
                            //正常流程开始检查升级
                            mHandler.sendEmptyMessage(CHECK_UPDATE);
                        }
                    });
                }
            }
        }
    }
}
