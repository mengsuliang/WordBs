package com.benben.wordtutor.utils;

public class Sentence_split {

    static public String getspit(String wordCN){
        String[] a=wordCN.split("\n");
        return a[0];
    }
}