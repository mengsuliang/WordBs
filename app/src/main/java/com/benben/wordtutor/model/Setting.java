package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

//设置信息类
public class Setting extends BmobObject {
    private int _id;        //Id
    private int sock;       //是否开启了锁屏背单词，0：否 1：是
    private String difficulty ;     //难度
    private int newnum;     //每天需要新背的单词数量
    private int socknum;    //解锁需要背的单词数量
    private int theme;   //主题
    private String userId; //用户登录Id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Setting(int _id, int sock, String difficulty, int newnum, int socknum, int theme, String userId) {
        this._id = _id;
        this.sock = sock;
        this.difficulty = difficulty;
        this.newnum = newnum;
        this.socknum = socknum;
        this.theme = theme;
        this.userId = userId;
    }

    //默认构造方法
    public Setting(){ super(); }
    //定义有参数构造方法，初始化信息实体中的字段
    public Setting(int _id, int sock, String difficulty, int newnum, int socknum, int theme) {
        this._id = _id;
        this.sock = sock;
        this.difficulty = difficulty;
        this.newnum = newnum;
        this.socknum = socknum;
        this.theme = theme;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getSock() {
        return sock;
    }

    public void setSock(int sock) {
        this.sock = sock;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getNewnum() {
        return newnum;
    }

    public void setNewnum(int newnum) {
        this.newnum = newnum;
    }

    public int getSocknum() {
        return socknum;
    }

    public void setSocknum(int socknum) {
        this.socknum = socknum;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }
}
