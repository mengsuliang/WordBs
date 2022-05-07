package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

public class MWordStudyRecord extends BmobObject {
    //单词序号
    private Integer wordId;
    //用户名
    private String username;
    //生词标记
    private Boolean isUnknown;
    //是否错误
    private Boolean isFalse;
    //背诵开始时间
    private String startTime;
    //背诵结束时间
    private String endTime;

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getUnknown() {
        return isUnknown;
    }

    public void setUnknown(Boolean unknown) {
        isUnknown = unknown;
    }

    public Boolean getFalse() {
        return isFalse;
    }

    public void setFalse(Boolean aFalse) {
        isFalse = aFalse;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "MWordStudyRecord{" +
                "wordId=" + wordId +
                ", username='" + username + '\'' +
                ", isUnknown=" + isUnknown +
                ", isFalse=" + isFalse +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
