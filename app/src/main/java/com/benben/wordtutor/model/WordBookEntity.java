package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

//单词库实体类
public class WordBookEntity extends BmobObject {
    public int imageResId;
    public String title;
    public int num;

    public WordBookEntity(int imageResId, String title, int num){
        this.imageResId = imageResId;
        this.title= title;
        this.num=num;
    }
}
