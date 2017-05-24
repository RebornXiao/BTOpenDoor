package com.xiex.btopendoor;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;


/**
 * Created by baby on 2016/12/14.
 * <p>
 * 宜步出行，天天速达
 */

public class MediaPlayerHelp {
    public static MediaPlayer mediaPlayer;

    public static void play1(Context context, int id) {
//        if (mediaPlayer == null) {
//            mediaPlayer = new MediaPlayer();
//        }
        mediaPlayer.reset();//恢复到未初始化的状态
        mediaPlayer = MediaPlayer.create(context, id);//读取音频
        try {
            mediaPlayer.prepare();    //准备
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();  //播放
    }

    public static void play(Context context, int id) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();//恢复到未初始化的状态
        mediaPlayer = MediaPlayer.create(context, id);
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(context, id);
            }
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }


}
