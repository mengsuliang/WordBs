package com.benben.wordtutor.model;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class MUser extends BmobUser implements Serializable {
    //昵称
    private String nickname;
    //头像文件
    private BmobFile avatar;
    //日计划单词数量
    private Integer dayWordNum;
    //计划背诵单词本
    private String planWordBook;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public Integer getDayWordNum() {
        return dayWordNum;
    }

    public void setDayWordNum(Integer dayWordNum) {
        this.dayWordNum = dayWordNum;
    }

    public String getPlanWordBook() {
        return planWordBook;
    }

    public void setPlanWordBook(String planWordBook) {
        this.planWordBook = planWordBook;
    }


}
