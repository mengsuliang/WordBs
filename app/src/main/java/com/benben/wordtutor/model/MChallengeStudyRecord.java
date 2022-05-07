package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

public class MChallengeStudyRecord extends BmobObject {
    //用户名
    private String username;
    //上次挑战分数
    private Integer preScore;
    //历史最高分数
    private Integer maxScore;
    //单词本名称
    private String wordBook;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPreScore() {
        return preScore;
    }

    public void setPreScore(Integer preScore) {
        this.preScore = preScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public String getWordBook() {
        return wordBook;
    }

    public void setWordBook(String wordBook) {
        this.wordBook = wordBook;
    }
}
