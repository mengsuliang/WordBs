package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

public class MWordBook extends BmobObject {
    //序号
    private Integer id;
    //单词书名
    private String wordBook;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWordBook() {
        return wordBook;
    }

    public void setWordBook(String wordBook) {
        this.wordBook = wordBook;
    }
}
