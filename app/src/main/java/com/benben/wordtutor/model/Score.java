package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

//分数类
public class Score extends BmobObject {
    private int _id;        //Id
    private int maxScore;   //历史最高成绩
    private int preScore;   //上次最高成绩
    private int userId;     //用户登录Id

    public Score(int maxScore, int preScore, int userId) {
        this.maxScore = maxScore;
        this.preScore = preScore;
        this.userId = userId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getPreScore() {
        return preScore;
    }

    public void setPreScore(int preScore) {
        this.preScore = preScore;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
