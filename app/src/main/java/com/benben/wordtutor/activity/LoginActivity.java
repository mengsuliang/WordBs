package com.benben.wordtutor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.wordtutor.dao.UserDao;

import com.benben.wordtutor.R;
import com.benben.wordtutor.model.WUser;
import com.orhanobut.hawk.Hawk;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements  View.OnClickListener{

    public static final String USER_INFO = "userInfo";


    private UserDao userDao;
    private TextView mTvRegister, mTvLogin;
    private EditText etAccount, etPass;
    private Toast toast;
    private ImageView ivPwdSwitch; //密码隐藏
    private Boolean bPwdSwitch = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        etAccount = findViewById(R.id.et_account);
        etPass = findViewById(R.id.et_pass);
        ivPwdSwitch = findViewById(R.id.iv_showPassword);
        ivPwdSwitch.setOnClickListener(this);

        mTvRegister = findViewById(R.id.tv_register);
        mTvLogin = findViewById(R.id.tv_login);
        mTvRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);

        MyTextWatcher watcher = new MyTextWatcher();
        etAccount.addTextChangedListener(watcher);
        etPass.addTextChangedListener(watcher);

        userDao = new UserDao(this);


    }

    @Override
    protected void initData() {
        super.initData();

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_register:
                intent.setClass(this, RegisterActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.tv_login:
                String account = etAccount.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if(TextUtils.isEmpty(account)){
                    showToast(getString(R.string.请输入账号));
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    showToast(getString(R.string.请输入密码));
                    return;
                }

                login(this,account,pass);
                break;

            //密码隐藏
            case R.id.iv_showPassword:
                bPwdSwitch = !bPwdSwitch;

                if (bPwdSwitch){
                    ivPwdSwitch.setImageResource(
                            R.drawable.icon_eyes_show);
                    etPass.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    ivPwdSwitch.setImageResource(
                            R.drawable.icon_eyes_hidden);
                    //普通文本类型|密码不可见
                    etPass.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPass.setTypeface(Typeface.DEFAULT);
                }
                break;
        }


    }

    private void login(Context context, String phone, String pass) {
        WUser wUser=new WUser();
        wUser.setUsername(phone);
        wUser.setPassword(pass);
        wUser.login(new SaveListener<WUser>() {
            @Override
            public void done(WUser user, BmobException e) {
                if (e == null) {
                    WUser wUser = BmobUser.getCurrentUser(WUser.class);
                    Log.d("Test001", "done: "+wUser.getObjectId());
                    Hawk.put("userToken",phone+"-"+pass);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast(getString(R.string.账号不存在或密码错误));
                }
            }
        });


//        User user = new User(phone, pass);
//        User login = userDao.login(user);
//        if(login!=null){
//            Hawk.put("userToken",phone+"-"+pass);
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }else{
//            showToast(getString(R.string.账户名不存在或密码错误));
//        }
    }

    protected void showToast(String msg){


        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }


    /**
     * 监听输入文字的状态
     */
    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = etAccount.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pass)  && phone.length()>=11 && pass.length()>=6){
                mTvLogin.setSelected(true);
                mTvLogin.setEnabled(true);
            }else{
                mTvLogin.setSelected(false);
                mTvLogin.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
