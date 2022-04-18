package com.benben.wordtutor.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.wordtutor.R;
import com.benben.wordtutor.activity.FileImportActivity;
import com.benben.wordtutor.activity.WordListActivity;
import com.benben.wordtutor.adapter.WordBookAdapter;
import com.benben.wordtutor.dao.SettingDao;
import com.benben.wordtutor.dao.WordDao;
import com.benben.wordtutor.dao.WordTypeDao;
import com.benben.wordtutor.model.WordBookEntity;
import com.benben.wordtutor.model.WordType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WordBookFragment extends BaseFragment {

    private RecyclerView mRvWordBook;
    private SettingDao settingDao;
    private WordDao wordDao;
    private WordTypeDao wordTypeDao;
    private ArrayList<WordBookEntity> bookList;
    private WordBookAdapter wordBookAdapter;
    private AlertDialog.Builder builder;//添加单词本对话框
    private FloatingActionButton floatingActionButton;
    private String type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wordbook, container, false);
        mRvWordBook = view.findViewById(R.id.rv_wordbook);

        type = getActivity().getIntent().getStringExtra("refresh");
        wordTypeDao = new WordTypeDao(getContext());
        settingDao = new SettingDao(getContext());
        wordDao = new WordDao(getContext());
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_wordbook);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);//设置为3列
        mRvWordBook.setLayoutManager(layoutManager);
        bookList = getData();

        //导入单词本
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转文件选择器，导入单词本（模板文件）
                Intent intent = new Intent(getContext(), FileImportActivity.class);
                getContext().startActivity(intent);
            }
        });

        wordBookAdapter = new WordBookAdapter(getContext(), bookList);

        //广播通知刷新adapter
        if ("1".equals(type)){
            bookList.clear();
            bookList.addAll(getData());
            wordBookAdapter.notifyDataSetChanged();
        }

        wordBookAdapter.setOnItemListenerListener(new WordBookAdapter.OnItemListener() {

            //单击跳转到单词列表
            @Override
            public void OnItemClickListener(View view, int position) {
                String title;
                title = bookList.get(position).title;
                //Toast.makeText(v.getContext(),"你点击了"+word.getTranCN().toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), WordListActivity.class);
                intent.putExtra("title", title);
                getContext().startActivity(intent);

            }

            //长按编辑单词本
            @Override
            public void OnItemLongClickListener(View view, final int position) {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);//震动功能
                vibrator.vibrate(150);//震动时间
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.dialog_wordbook_edit, (ViewGroup) view.findViewById(R.id.dialog), false);
                EditText editText = (EditText) dialog.findViewById(R.id.text_name);
                EditText contextText = (EditText) dialog.findViewById(R.id.text_context);
                ImageView delete = dialog.findViewById(R.id.delete);
                WordType wordType = wordTypeDao.find(bookList.get(position).title);
                editText.setText(wordType.getWordType());
                contextText.setText(wordType.getContext());

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("编辑单词本:" + bookList.get(position).title);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().isEmpty()) {
                            WordType wordType = new WordType(editText.getText().toString(), contextText.getText().toString(), 0);
                            WordTypeDao wordTypeDao = new WordTypeDao(getContext());
                            String title = bookList.get(position).title;
                            wordTypeDao.update(wordType,title);
                            Toast.makeText(getContext(), "编辑成功", Toast.LENGTH_SHORT).show();
                            bookList.clear();
                            bookList.addAll(getData());
                            wordBookAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "单词本名称不能为空！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null);
                builder.setView(dialog);
                builder.setIcon(R.drawable.icon_edit);
                final AlertDialog dialog0 = builder.show();

                delete.setOnClickListener(new View.OnClickListener() {
                    //删除弹出框
                    @Override
                    public void onClick(View v) {
                        builder = new AlertDialog.Builder(getContext());
                        if (position >= bookList.size()) {
                            Toast.makeText(getContext(), "该单词本已被删除！", Toast.LENGTH_SHORT).show();
                        } else {
                            builder.setTitle("确认删除" + bookList.get(position).title).setMessage("是否确认删除,删除后不可恢复！");
                            builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    wordTypeDao = new WordTypeDao(getContext());
                                    WordType wordType = wordTypeDao.find(bookList.get(position).title);
                                    Log.d("hahaha", "onClick: " + wordType.getWordType());
                                    if (wordType.getIsSystem() == 0) {
                                        wordTypeDao.delete(wordType);
                                        bookList.clear();
                                        bookList.addAll(getData());
                                        wordBookAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                        //关闭对话框
                                        dialog0.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), "系统单词本无法删除", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).setNegativeButton("取消", null);
                            builder.setIcon(R.drawable.slide_about_icon);
                            builder.show();
                        }

                    }
                });

            }


        });
        mRvWordBook.setAdapter(wordBookAdapter);

        return view;
    }

    @Override
    protected View initView() {
        return null;
    }


    public ArrayList<WordBookEntity> getData() {
        ArrayList<WordBookEntity> mData = new ArrayList<>(0);
        List<WordType> title = wordTypeDao.getAllType();
        Iterator<WordType> iterator = title.iterator();
        while (iterator.hasNext()) {
            String wordType = iterator.next().getWordType();
            mData.add(new WordBookEntity(R.mipmap.icon_book, wordType, wordDao.getTypeCount(wordType)));
        }
        return mData;

    }

}
