package com.tangcco.TangccoAndroid026_37.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.tangcco.TangccoAndroid026_37.R;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class MyService1 extends Service {
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "MyService1.onCreate()........................");

        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.zdjcjdhx);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("MyService", "MyService1.onStart()........................");

//        int op = intent.getIntExtra("op",-1);
//        switch (op){
//            case 1:
//
//                break;
//            case 2:
//
//                break;
//        }

        play();
    }

    public void play() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService", "MyService1.onStartCommand()........................");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MyService", "MyService1.onBind()........................");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MyService", "MyService1.onUnbind()........................");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("MyService", "MyService1.onDestroy()........................");
        super.onDestroy();
        stop();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
                mediaPlayer.prepare();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    //40天
    //U2 Service ContentProvider SurfaceView 自定义View 项目答辩
    // 一个月  做项目 团队合作
    // 在此之前 带领一个项目

}
