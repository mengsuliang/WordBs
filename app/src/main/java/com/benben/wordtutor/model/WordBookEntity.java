package com.benben.wordtutor.model;

public class WordBookEntity {
    public int imageResId;
    public String title;
    public int num;

    public WordBookEntity(int imageResId, String title, int num){
        this.imageResId = imageResId;
        this.title= title;
        this.num=num;
    }
}
