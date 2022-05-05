package com.benben.wordtutor.bean;

import com.benben.wordtutor.model.Word;

import java.util.List;

/**
 * 用于将json数据转换为可操作的java对象
 */
public class WordsBean {
    private List<Word> words;

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "WordsBean{" +
                "words=" + words +
                '}';
    }
}
