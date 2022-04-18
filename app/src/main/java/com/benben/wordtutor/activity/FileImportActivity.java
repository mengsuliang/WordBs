package com.benben.wordtutor.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.benben.wordtutor.R;
import com.benben.wordtutor.fragment.WordBookFragment;
import com.benben.wordtutor.utils.ImportJsonUtils;
import com.hz.android.fileselector.FileSelectorView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileImportActivity extends AppCompatActivity {

    private FileSelectorView fileSelectorView;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_import);

        fileSelectorView = findViewById(R.id.file_selector_view);

        //切换目录
        fileSelectorView.setCurrentDirectory(new File(Environment.getExternalStorageDirectory(), "Android"));
        fileSelectorView.setTextSize(20);//设置文字大小
        fileSelectorView.setTextColor(Color.BLACK); //设置文字颜色
        fileSelectorView.setIconSize(60); //设置图标大小也就是设置放置图标的imageView的大小

        //设置选择文件的监听
        fileSelectorView.setOnFileSelectedListener(new FileSelectorView.OnFileSelectedListener() {
            @Override
            public void onSelected(File selectedFile) {

                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.dialog_wordbook_import, (ViewGroup) findViewById(R.id.dialog_wordBookName), false);
                EditText editText = (EditText) dialog.findViewById(R.id.text_wordBookName);

                builder = new AlertDialog.Builder(FileImportActivity.this);
                builder.setTitle("输入单词本名称" );
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String wordtype = editText.getText().toString();
                        Log.d("FileImoportActivity",wordtype);
                        if (!wordtype.isEmpty()){
                            Log.d("FileImoportActivity:filePath",selectedFile.getAbsolutePath());
                            if(selectedFile.exists()){
                                //1。创建一个File类的对象
                                //2.创建一个FileInputStream类的对象
                                FileInputStream fis=null;
                                try {
                                    File file=new File(selectedFile.getAbsolutePath());
                                    fis = new FileInputStream(file);
                                    ImportJsonUtils.importData(fis,wordtype, FileImportActivity.this);
                                    Intent intent = new Intent(FileImportActivity.this, WordBookFragment.class);
                                    intent.setAction("com.benben.wordtutor.BookList");
                                    sendBroadcast(intent);
                                    Toast.makeText(FileImportActivity.this, "导入成功！" , Toast.LENGTH_SHORT).show();
                                    finish();
                                } catch (FileNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } finally{
                                    //4.关闭响应的流
                                    if(fis!=null){
                                        try {
                                            fis.close();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                        }else {
                            Toast.makeText(FileImportActivity.this, "单词本名称不能为空！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null);
                builder.setView(dialog);
                builder.setIcon(R.drawable.icon_edit);
                builder.show();
            }

            @Override
            public void onFilePathChanged(File file) {

            }
        });

    }

}