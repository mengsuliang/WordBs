package com.benben.wordtutor.model;

import cn.bmob.v3.BmobObject;

public class MWord extends BmobObject {

    private int id;        //单词编号
    private int wordRank;   //在本类型中的序号
    private String headWord; //单词
    private String sentences;   //例句
    private String usphone;     //美式音标
    private String ukphone;     //英式音标
    private String syno;        //同近词
    private String phrases;     //词组
    private String tranCN;      //中文释义
    private String tranEN;      //英文释义
    private String wordBook;      //类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordRank() {
        return wordRank;
    }

    public void setWordRank(int wordRank) {
        this.wordRank = wordRank;
    }

    public String getHeadWord() {
        return headWord;
    }

    public void setHeadWord(String headWord) {
        this.headWord = headWord;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public String getUsphone() {
        return usphone;
    }

    public void setUsphone(String usphone) {
        this.usphone = usphone;
    }

    public String getUkphone() {
        return ukphone;
    }

    public void setUkphone(String ukphone) {
        this.ukphone = ukphone;
    }

    public String getSyno() {
        return syno;
    }

    public void setSyno(String syno) {
        this.syno = syno;
    }

    public String getPhrases() {
        return phrases;
    }

    public void setPhrases(String phrases) {
        this.phrases = phrases;
    }

    public String getTranCN() {
        return tranCN;
    }

    public void setTranCN(String tranCN) {
        this.tranCN = tranCN;
    }

    public String getTranEN() {
        return tranEN;
    }

    public void setTranEN(String tranEN) {
        this.tranEN = tranEN;
    }

    public String getWordBook() {
        return wordBook;
    }

    public void setWordBook(String wordBook) {
        this.wordBook = wordBook;
    }
}
