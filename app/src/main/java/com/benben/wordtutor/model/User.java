package com.benben.wordtutor.model;


import java.io.Serializable;
import cn.bmob.v3.BmobObject;

//用户实体类
public class User extends BmobObject implements Serializable {
    private int _id;
    private String username;
    private String password;

    //默认构造方法
    public User() {             //默认构造方法
        super();
    }

    //定义有参数构造方法，初始化信息实体中的字段
    public User(int id, String username, String password) {
        this._id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
