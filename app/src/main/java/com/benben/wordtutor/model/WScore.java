package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

public class WScore extends BmobObject {
    //序号
    private Integer id;        //Id
    //挑战最大分数
    private Integer maxScore;       //
    //挑战的目前分数
    private Integer preScore;
    //挑战的用户名
    private String username;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getPreScore() {
        return preScore;
    }

    public void setPreScore(Integer preScore) {
        this.preScore = preScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "WScore{" +
                "id=" + id +
                ", maxScore=" + maxScore +
                ", preScore=" + preScore +
                ", username='" + username + '\'' +
                '}';
    }
}
