package com.benben.wordtutor;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.benben.wordtutor.fragment.ChallengeFragment;
import com.benben.wordtutor.fragment.HomeFragment;
import com.benben.wordtutor.fragment.ReviewFragment;
import com.benben.wordtutor.fragment.WordBookFragment;

import com.benben.wordtutor.utils.Api;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    private static final String TAG = "jyw";
    public static final String XMPUSH_APP_ID = "2882303761518326717";
    public static final String XMPUSH_APP_KEY = "5231832633717";
    public static final String Bomb_APP_ID= "e1a07e9784e5ab8a35ca88c2abf20fac";
    private List<Fragment> fragments = new ArrayList<>();
    private static Context context;
    private Gson gson =new Gson();

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //默认初始化
        Bmob.initialize(this, Bomb_APP_ID);
        Hawk.init(context).build();
        initMainFragment();


        //initLitpal();


        initUserInfo();


        //初始化全局参数
        initSmartRefreshParams();
    }

    private void initSmartRefreshParams() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private void initUserInfo() {
        String token = Hawk.get(Api.USER_TOKEN_KEY, "");
        String userId = Hawk.get(Api.USER_ID_KEY, "0");
        Api.TOKEN = token;

        try {
            //取出缓存的userId
            Api.userId = Integer.parseInt(userId);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }



//    private void initLitpal() {
//        LitePal.initialize(this);
//    }



    private void initMainFragment() {

        fragments.add(new HomeFragment());
        fragments.add(new ReviewFragment());
        fragments.add(new WordBookFragment());
        fragments.add(new ChallengeFragment());
    }

    public List<Fragment> getFragments() {
        return fragments;
    }


    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplicationInfo().processName;
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
