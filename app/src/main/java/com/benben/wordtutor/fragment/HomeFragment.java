package com.benben.wordtutor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benben.wordtutor.R;
import com.benben.wordtutor.activity.MemoryWordMainActivity;
import com.benben.wordtutor.activity.WordReviewActivity;
import com.benben.wordtutor.dao.SettingDao;
import com.benben.wordtutor.dao.StudyRecordDao;
import com.benben.wordtutor.dao.WordDao;
import com.benben.wordtutor.dao.WordRecordDao;
import com.benben.wordtutor.listener.IDialogBtnClickCallBack;
import com.benben.wordtutor.model.StudyRecord;
import com.benben.wordtutor.utils.Api;
import com.benben.wordtutor.utils.DialogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    private Context context;
    private SettingDao settingDao;
    private TextView mTvDifficalt,mTvTotalNum,mTvHasBack,mTvNoStudy,mTvNeedReview,mBtnStart;
    private WordDao wordDao;
    private WordRecordDao wordRecordDao;
    private StudyRecordDao studyRecordDao;
    private ImageView homePageImgView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        settingDao = new SettingDao(context);
        wordDao = new WordDao(context);
        wordRecordDao = new WordRecordDao(context);
        studyRecordDao = new StudyRecordDao(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        mTvDifficalt = view.findViewById(R.id.tv_difficalt);
        mTvTotalNum = view.findViewById(R.id.tv_totalNum);
        mTvHasBack = view.findViewById(R.id.tv_hasBack);
        mTvNoStudy = view.findViewById(R.id.tv_nostudy);
        mTvNeedReview = view.findViewById(R.id.tv_needreview);
        mBtnStart = view.findViewById(R.id.btn_start);
        homePageImgView = view.findViewById(R.id.home_page);

        mTvNoStudy.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);

        initData();
        return view;
    }

    @Override
    protected View initView() {
        return null;
    }

    public void initData() {
        String difficulty = settingDao.getDifficulty();
        int newNum = settingDao.getNewNum();
        int sockNum = settingDao.getSockNum();
        int allWordCount = wordDao.getAllWordCount();
        int memoryTotalWordSize = wordRecordDao.getMemoryTotalWordSize();
        int typeReWordCount = wordRecordDao.getTypeReWordCount();
        Log.d(TAG, "onCreateView: 当前难度 == "+difficulty);
        mTvDifficalt.setText(difficulty);
        mTvTotalNum.setText(""+allWordCount);
        mTvHasBack.setText(memoryTotalWordSize+"");
        StudyRecord studyRecord = studyRecordDao.addOrGet();
        int todayNeedNewNum = studyRecord.getNeedNewNum() - studyRecord.getNewNum();
        mTvNoStudy.setText(todayNeedNewNum +"");
        mTvNeedReview.setText(typeReWordCount+"");

        //加载首页GIF动图
        Glide.with(this)
                .load(R.drawable.img_gif1)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(homePageImgView);

        String hasYestoday = studyRecordDao.isHasYestodayData();
        if(hasYestoday!=null && !Api.isShowReview){
            Api.isShowReview = true;
            DialogUtils.showDialog(getContext(), "需要复习昨天的单词吗？", "昨天有背单词奥，最好复习一下！！",
                    "去复习", "取消", new IDialogBtnClickCallBack() {
                        @Override
                        public void onPositiveButtonClicked() {
                            Intent intent = new Intent(getContext(), WordReviewActivity.class);
                            String nowDate = hasYestoday;
                            intent.putExtra("date",nowDate);
                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
        }
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_start:
                intent.setClass(getContext(), MemoryWordMainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_nostudy:

                break;
        }
    }
}
