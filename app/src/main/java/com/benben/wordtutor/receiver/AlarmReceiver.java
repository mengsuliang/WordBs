package com.benben.wordtutor.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.benben.wordtutor.MyApplication;
import com.benben.wordtutor.R;
import com.benben.wordtutor.activity.MainActivity;
import com.benben.wordtutor.activity.RingActivity;

//接收者
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if("com.benben.wordtutor.RING".equals(intent.getAction())){
            //跳转到RingActivity
            Intent intent1= new Intent(context, RingActivity.class);
            //给Intent设置标志位
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getContext());
//            builder.setTitle("小朋友，时间到啦")
//                    .setMessage("请注意休息一下啦！不要让眼睛过度疲劳哦~")
//                    //点击对话框以外的区域是否让对话框消失
//                    .setCancelable(false)
//                    //设置正面按钮
//                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    //设置反面按钮
//                    //.setNegativeButton("-", null)
//                    .show();

        }

    }
}
