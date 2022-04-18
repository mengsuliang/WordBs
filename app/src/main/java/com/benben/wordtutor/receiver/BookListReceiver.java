package com.benben.wordtutor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import androidx.fragment.app.Fragment;

import com.benben.wordtutor.MyApplication;
import com.benben.wordtutor.fragment.WordBookFragment;

import java.util.ArrayList;

public class BookListReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //广播接收器
        if ("com.benben.wordtutor.BookList".equals(intent.getAction())){
            Intent intent1 = new Intent(context, WordBookFragment.class);
            intent1.putExtra("refresh","1");
            context.startActivity(intent1);
        }
    }


}
