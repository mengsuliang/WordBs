package com.benben.wordtutor.utils;

import android.content.Context;
import android.content.Intent;

public class MyRoute {
    public static void go(Context context, String url) {
        if(!AuthUtils.hasLogin()){
            AuthUtils.goLogin(context);
            return;
        }


    }
}
