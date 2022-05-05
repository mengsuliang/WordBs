package com.benben.wordtutor.activity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.benben.wordtutor.MyApplication;
import com.benben.wordtutor.R;
import com.benben.wordtutor.bean.MessageEvent;
import com.benben.wordtutor.dao.ScoreDao;
import com.benben.wordtutor.dao.SettingDao;
import com.benben.wordtutor.dao.UserDao;
import com.benben.wordtutor.fragment.HomeFragment;
import com.benben.wordtutor.model.User;
import com.benben.wordtutor.model.WUser;
import com.benben.wordtutor.utils.Api;
import com.benben.wordtutor.utils.DialogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private List<Fragment> fragments;
    //当前主页显示的Fragment
    private Fragment mCurrentFragment;
    private FragmentManager fragmentManager;
    private SettingDao settingDao;
    private TextView wordType;

    private TextView mTvSelect;
    private TextView mTvWordType;
    private ImageView drawerImgView;

    private ScoreDao scoreDao;
    private UserDao userDao;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //取消bottomNavigation的图标覆盖颜色
        bottomNavigation.setItemIconTintList(null);
        //设置bottomNavigation的字体显示模式 大于3个也是全部显示
        bottomNavigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //设置底部导航标签的点击监听
        bottomNavigation.setOnNavigationItemSelectedListener(mNavigationListener);

        settingDao = new SettingDao(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch(menuItem.getItemId()){
                    case R.id.userINfo:
                        Toast.makeText(MainActivity.this,"开发中…",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.updatePass:
                        intent = new Intent(MainActivity.this, RegisterActivity.class);
                        intent.putExtra("type","1");
                        startActivity(intent);
                        break;
                    case R.id.remind:
                        intent = new Intent(MainActivity.this,AlarmActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("关于作者")
                                .setMessage(R.string.关于作者)
                                .setIcon(R.drawable.ic_baseline_info_24)
                                //点击对话框以外的区域是否让对话框消失
                                .setCancelable(true)
                                //设置正面按钮
                                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                //设置反面按钮
                                //.setNegativeButton("-", null)
                                .show();
                        break;
                    case R.id.loginout:
                        Hawk.put("userToken","");
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 点击了导航 == ");
                drawerLayout.openDrawer(Gravity.LEFT);
                //加载侧滑页GIF动图
//                Glide.with(v)
//                        .load(R.drawable.img_gif2)
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                        .into(drawerImgView);
            }
        });

        wordType = findViewById(R.id.wordType);
        wordType.setText(settingDao.getDifficulty());
        mTvSelect = findViewById(R.id.select);
        mTvWordType = findViewById(R.id.wordType);
        drawerImgView = findViewById(R.id.drawer_page);
        mTvSelect.setOnClickListener(this);
        mTvWordType.setOnClickListener(this);

        initApp();

        //查询用户并创建分数
        userDao = new UserDao(this);
        scoreDao = new ScoreDao(this);
        createScoreTable();

    }


    //创建成绩表
    private void createScoreTable() {
        String userToken = Hawk.get("userToken");
        if(!TextUtils.isEmpty(userToken)){
            String[] split = userToken.split("-");
            String name = split[0];
            String pass = split[1];
            if(TextUtils.isEmpty(name)){
                return;
            }
            if(TextUtils.isEmpty(pass)){
                return;
            }

//            h: 修改成从缓存中获取当前用户数据178112212
            WUser wUser = BmobUser.getCurrentUser(WUser.class);
            //User user = new User(name,pass);
            Api.userId = wUser.getId();
            //User storeUser = userDao.login(user);
            scoreDao.updateUserID(wUser.getId()+"");
            Log.d(TAG, "createScoreTable: 执行了创建成绩表...");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case R.id.wordtype:
            case R.id.select:
                DialogUtils.showEditSubCateDialog(this,"选择难度","",
                        "确定","取消",new DialogUtils.ISubCateDialogClickListener(){
                            @Override
                            public void onPositiveButtonClicked() {
                                Log.d(TAG, "onPositiveButtonClicked: 确定...");
                                wordType.setText(settingDao.getDifficulty());
                                Fragment fragment = fragments.get(0);
                                if(fragment instanceof HomeFragment){
                                    HomeFragment homeFragment = (HomeFragment) fragment;
                                    homeFragment.initData();
                                }

                            }

                            @Override
                            public void onNegativeButtonClicked() {
                                Log.d(TAG, "onNegativeButtonClicked: 取消...");

                            }
                        });
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: EventBus...");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event!=null){
            switch (event.getType()){
                case "login_success":
                    Hawk.put(Api.USER_TOKEN_KEY, Api.TOKEN);
                    break;
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onDestroy: EventBus...");

    }

    /**
     * 初始化app
     */
    private void initApp() {

        fragmentManager = getSupportFragmentManager();

        Application application = getApplication();
        if(application instanceof MyApplication){
            MyApplication app = (MyApplication) application;
            fragments = app.getFragments();
        }

        if(fragments!=null && fragments.size()>0){
            Fragment mHomeFragment = fragments.get(0);
            mCurrentFragment = mHomeFragment;

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(!mCurrentFragment.isAdded()){
                transaction.add(R.id.fl_content, mCurrentFragment);
            }else{
                transaction.show(mCurrentFragment);
            }

            transaction.commit();

        }


    }


    @Override
    protected void initData() {
        super.initData();
        Log.d(TAG, "initData: MainActivity 数据初始化了");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged:  === "+newConfig.getLayoutDirection());
    }

    /**
     * 选择或切换页面显示
     * @param fromFragment
     * @param toFragment
     */
    private boolean switchFragment(Fragment fromFragment, Fragment toFragment) {
        if(fromFragment!=toFragment){
            if(fragmentManager==null){
                return false;
            }

            if(toFragment==null){
                return false;
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(!toFragment.isAdded()){
                transaction.add(R.id.fl_content, toFragment);
            }else{
                transaction.show(toFragment);
            }

            if(fromFragment!=null){
                transaction.hide(fromFragment);
            }

            transaction.commit();
            mCurrentFragment = toFragment;
            return true;

        }

        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment toFragment = null;

            switch (item.getItemId()) {
                case R.id.recite:
                    Log.d(TAG, "onNavigationItemSelected: 背词");
                    if(fragments!=null){
                        toFragment = fragments.get(0);
                    }
                    break;

                case R.id.review:
                    Log.d(TAG, "onNavigationItemSelected: 复习");
                    toFragment = fragments.get(1);
                    break;

                case R.id.word_lib:
                    Log.d(TAG, "onNavigationItemSelected: 词库");
                    toFragment = fragments.get(2);
                    break;

                case R.id.challenge:
                    Log.d(TAG, "onNavigationItemSelected: 挑战");
                    toFragment = fragments.get(3);
                    break;
            }

            boolean success = switchFragment(mCurrentFragment, toFragment);

            return success;
        }
    };


}
