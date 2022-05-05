package com.benben.wordtutor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benben.wordtutor.R;
import com.benben.wordtutor.dao.UserDao;
import com.benben.wordtutor.model.User;
import com.benben.wordtutor.model.WScore;
import com.benben.wordtutor.model.WUser;
import com.orhanobut.hawk.Hawk;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    public static final String USER_INFO = "userInfo";
    private EditText etPhone,etPass,etPass2;
    private TextView tvRegister,mTvTag;
    private Toast toast;
    private UserDao userDao;
    private String type;
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        type = getIntent().getStringExtra("type");
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
        mTvTag = findViewById(R.id.tag1);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        etPhone = findViewById(R.id.et_phone);
        etPass = findViewById(R.id.et_pass);
        etPass2 = findViewById(R.id.et_pass2);
        tvRegister = findViewById(R.id.tv_register);

        tvRegister.setOnClickListener(this);

        MyTextWatcher watcher = new MyTextWatcher();
        etPhone.addTextChangedListener(watcher);
        etPass.addTextChangedListener(watcher);
        etPass2.addTextChangedListener(watcher);

        userDao = new UserDao(this);

        if("1".equals(type)){
            mTvTag.setText("修改密码");
            tvRegister.setText("更新");


            if (BmobUser.isLogin()){
                etPhone.setText(BmobUser.getCurrentUser(WUser.class).getUsername());
                etPhone.setEnabled(false);
            }else
                return;
//
//            String userToken = Hawk.get("userToken");
//            if(!TextUtils.isEmpty(userToken)){
//                String[] split = userToken.split("-");
//                String name = split[0];
//                String pass = split[1];
//                if(TextUtils.isEmpty(name)){
//                    return;
//                }
//                if(TextUtils.isEmpty(pass)){
//                    return;
//                }
//
//                //设为不可编辑
//                etPhone.setText(name);
//                etPhone.setEnabled(false);
//                //etPhone.setKeyListener(null);



        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_register:
                String phone = etPhone.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String pass2 = etPass2.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    showToast(getString(R.string.请输入手机号));
                    return;
                }

                if(!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(pass2)){
                    if(!pass.equals(pass2)){
                        //两次输入密码必须一致
                        showToast(getString(R.string.密码必须一致));
                        return;
                    }
                }else{
                    showToast(getString(R.string.请输入密码));
                    return;
                }


                if("1".equals(type)){
                    requestUpdatePass(this,pass);
                }else{
                    requestRegister(phone,pass,pass2);
                }


                break;
        }
    }

    /**
     * 请求注册
     * @param phone
     * @param pass
     * @param pass2
     */
    private void requestRegister(String phone, String pass, String pass2) {

        WUser wUser=new WUser();
        wUser.setUsername(phone);
        wUser.setPassword(pass);
        wUser.signUp(new SaveListener<WUser>() {
            @Override
            public void done(WUser wUser, BmobException e) {
                if (e == null) {
                    showToast(getString(R.string.注册成功));
                    //注册成功后创建对应的分数表
                    creatScoreTable(wUser);

                    finish();
                    //Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    showToast(getString(R.string.注册失败)+": "+e.getLocalizedMessage());
                   // Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });


////
////
//        User user = new User(phone, pass);
//        User exitsBean = userDao.findByName(user);
//        if(exitsBean!=null){
//            showToast(getString(R.string.账户名已存在));
//            return;
//        }
//
//        long rowId = userDao.add(user);
//        if(rowId>0){
//            showToast(getString(R.string.注册成功));
//            finish();
//        }else{
//            showToast(getString(R.string.注册失败));
//        }
    }

    /**
     * 请求注册
     * @param context
     * @param pass
     *
     */
    private void requestUpdatePass(Context context, String pass) {

        WUser wUser = BmobUser.getCurrentUser(WUser.class);
        wUser.setPassword(pass);
        wUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e){
                    showToast(getString(R.string.更新密码成功));
                Intent intent = new Intent();
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
                }else {
                    showToast(getString(R.string.更新密码成功));
                }
            }
        });




//
//
//        User user = new User(phone, pass);
//        User exitsBean = userDao.findByName(user);
//        if(exitsBean==null){
//            showToast(getString(R.string.账户不存在));
//            return;
//        }
//
//        user.set_id(exitsBean.get_id());
//        long rowId = userDao.update(user);
//        if("1".equals(type)){
//            if(rowId>0){
//                showToast(getString(R.string.更新密码成功));
//                Intent intent = new Intent();
//                intent.setClass(this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }else{
//                showToast(getString(R.string.更新密码失败));
//            }
//
//        }else{
//            if(rowId>0){
//                showToast(getString(R.string.注册成功));
//                finish();
//            }else{
//                showToast(getString(R.string.注册失败));
//            }
//        }

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
            String phone = etPhone.getText().toString().trim();
            String pass = etPass.getText().toString().trim();
            String pass2 = etPass2.getText().toString().trim();

            if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(pass2)  && phone.length()>=11 && pass.length()>=6){
                tvRegister.setSelected(true);
                tvRegister.setEnabled(true);
            }else{
                tvRegister.setSelected(false);
                tvRegister.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    protected void showToast(String msg){
        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }

    private void creatScoreTable(WUser wUser){
        WScore wScore = new WScore();
        wScore.setMaxScore(0);
        wScore.setPreScore(0);
        wScore.setUsername(wUser.getUsername());
        wScore.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.d(TAG, "createScoreTable: 执行了创建成绩表，并创建成功了"+s);
                }else {
                    Log.d(TAG, "createScoreTable: 执行了创建成绩表，并创建失败了");
                }
            }
        });

    }

}

