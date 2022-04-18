package com.benben.wordtutor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.benben.wordtutor.fragment.WordBookFragment;

public class BookListReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.benben.wordtutor.BookList".equals(intent.getAction())){
            Intent intent1 = new Intent(context, WordBookFragment.class);
            intent1.putExtra("refresh","1");
            context.startActivity(intent1);
        }
    }
}
