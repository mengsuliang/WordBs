package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

public class WWord extends BmobObject {

    private int id;        //单词编号
    private int wordRank;   //在本类型中的编号
    private String headWord; //单词
    private String sentences;   //例句
    private String usphone;     //美式音标
    private String ukphone;     //英式音标
    private String syno;        //同近词
    private String phrases;     //词组
    private String tranCN;      //中文释义
    private String tranEN;      //英文释义
    private String wordType;      //类型



}
