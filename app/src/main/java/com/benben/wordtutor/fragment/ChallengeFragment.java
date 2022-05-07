package com.benben.wordtutor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benben.wordtutor.R;
import com.benben.wordtutor.activity.ChallengeWordActivity;
import com.benben.wordtutor.dao.ScoreDao;
import com.benben.wordtutor.dao.SettingDao;
import com.benben.wordtutor.dao.StudyRecordDao;
import com.benben.wordtutor.dao.WordDao;
import com.benben.wordtutor.dao.WordRecordDao;
import com.benben.wordtutor.model.Score;
import com.benben.wordtutor.model.WScore;
import com.benben.wordtutor.model.WUser;
import com.benben.wordtutor.utils.Api;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChallengeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private static final int REQUEST_CHALLENGE = 0x100;

    private Context context;
    private SettingDao settingDao;
    private TextView mTvDifficalt, mTvTotalNum, mTvHasBack, mTvNoStudy, mTvNeedReview, mBtnStart;
    private WordDao wordDao;
    private WordRecordDao wordRecordDao;
    private StudyRecordDao studyRecordDao;
    private ScoreDao scoreDao;
    private LinearLayout llScore;
    private TextView tvMaxScore;
    private TextView tvPreScore;
    private ImageView challengePageImgView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        settingDao = new SettingDao(context);
        wordDao = new WordDao(context);
        scoreDao = new ScoreDao(context);
        wordRecordDao = new WordRecordDao(context);
        studyRecordDao = new StudyRecordDao(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);

        mBtnStart = view.findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
        llScore = view.findViewById(R.id.ll_score);
        tvMaxScore = view.findViewById(R.id.tv_maxscore);
        tvPreScore = view.findViewById(R.id.tv_prescore);
        challengePageImgView = view.findViewById(R.id.challenge_page);

        initData(context);
        return view;
    }

    @Override
    protected View initView() {
        return null;
    }

    public void initData(Context context) {


        WUser currentUser = BmobUser.getCurrentUser(WUser.class);
        BmobQuery<WScore> wScoreQuery = new BmobQuery<>();
        wScoreQuery.addWhereEqualTo("username",currentUser.getUsername());
        wScoreQuery.findObjects(new FindListener<WScore>() {
            @Override
            public void done(List<WScore> list, BmobException e) {
                WScore wScore = list.get(0);
                int maxScore = wScore.getMaxScore();
                Api.maxScore = maxScore;
                Api.score_objectId = wScore.getObjectId();
                if(wScore.getMaxScore()<=0 && wScore.getPreScore()<=0){
                    llScore.setVisibility(View.GONE);
                }else{
                    llScore.setVisibility(View.VISIBLE);
                    tvMaxScore.setText(wScore.getMaxScore()+"");
                    tvPreScore.setText(wScore.getPreScore()+"");
                }



            }
        });
        //加载挑战页GIF动图
        Glide.with(context)
                .load(R.drawable.img_gif3)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(challengePageImgView);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_start:
                intent.setClass(getContext(), ChallengeWordActivity.class);
                startActivityForResult(intent,REQUEST_CHALLENGE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: 从挑战单词返回了...");
        initData(context);
    }
}
