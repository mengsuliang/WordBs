package com.benben.wordtutor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benben.wordtutor.R;

import java.util.List;

public class StudyDayAdapter extends BaseRvAdapter<String> {

    private static final String TAG = "jyw";
    private TextView tvDay,tvReview;
    private OnItemClickListener mListener;

    public StudyDayAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (data != null && data.size() > 0) {
            tvDay = holder.getView(R.id.tv_day);
            tvReview = holder.getView(R.id.review);
            tvDay.setText(data.get(position));
            tvReview.setText("进入复习");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onItemClicked(data.get(position));
                    }
                }
            });
        }
    }



    @Override
    protected int getLayoutResourceId(int viewType) {
        return R.layout.study_day_item;
    }


    @Override
    public void setData(List<String> data) {
        super.setData(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }




    public interface OnItemClickListener {
        void onItemClicked(String date);
    }
}
