package com.benben.wordtutor.utils;


import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.benben.wordtutor.dao.WordDao;
import com.benben.wordtutor.dao.WordTypeDao;
import com.benben.wordtutor.model.Word;
import com.benben.wordtutor.model.WordType;
import com.benben.wordtutor.model.WordsBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * 1.转换json数据为符合格式的对象
 * 2.负责将List数据存入到数据库中
 */
public class ImportJsonUtils {

    /**
     *
     * @param stream 文件流 or 这里可以改写成文件路径
     * @param wordType 词库类型 如CET4\CET6
     * @param context activity this活动上下文
     */
    public static void importData(InputStream stream, String wordType, Context context){
        WordDao wordDao=new WordDao(context);
        WordTypeDao wordTypeDao = new WordTypeDao(context);
        Gson gson = new Gson();
        WordsBean wordsBean=null;
        String json = "";
        System.out.println("111");
        try {
           //如果提供的是路径，可以取消此处的注释，并new一个文件输入流
            //File file = new File(filename);
            Reader reader = new InputStreamReader(stream, "utf-8");
            //FileInputStream fileInputStream = new FileInputStream(file);
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);
            }
            reader.close();
            json = buffer.toString();
            Log.d("ImportJsonUtils:json", json);
            wordsBean = gson.fromJson(json, WordsBean.class);
            //JsonReader jsonReader = new JsonReader(new StringReader(json));
            //jsonReader.setLenient(true);
            //wordsBean = gson.fromJson(String.valueOf(jsonReader), WordsBean.class);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        List<Word> words = wordsBean.getWords();
        WordType wordType1 = new WordType(wordType,wordType,0);
        wordTypeDao.add(wordType1);
        for (Word word: words) {
            word.setWordType(wordType);
            wordDao.add(word); //添加到数据库中
            //System.out.println(word);
        }


    }


}
