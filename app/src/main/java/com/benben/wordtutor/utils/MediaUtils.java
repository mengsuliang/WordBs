package com.benben.wordtutor.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

public class MediaUtils {
    private static MediaPlayer mediaPlayer;
    private static final String TAG = "MediaUtils";

    static{
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放音乐
     *
     * @param fileName
     */
    public static void playMp3(Context context, String fileName) {
        if(fileName.equals("end")){
            for(int i=0;i<5;i++){
                playSound(context, fileName+".mp3");
            }
        }else{
            playSound(context, fileName+".mp3");
        }


    }


    public static void playWord(Context context, Uri uri) {

        playSoundByUri(context, uri);

    }

    private static void playSoundByUri(Context context, Uri uri) {
        if (uri == null) {
            return;
        }
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.d(TAG, "playSoundByUri: 播放完成...");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    private static void playSound(Context context,String fileName) {
        if (TextUtils.isEmpty(fileName)) {

            return;
        }
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
