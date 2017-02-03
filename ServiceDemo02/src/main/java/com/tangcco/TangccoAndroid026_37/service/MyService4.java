package com.tangcco.TangccoAndroid026_37.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class MyService4 extends Service {
    private MediaRecorder mediaRecorder;
    private TelephonyManager manager;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new PhoneListener(),
                PhoneStateListener.LISTEN_CALL_STATE);
    }


    class PhoneListener extends PhoneStateListener{
        String incomingNumber = "";
        File file;
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:// 来电
                    this.incomingNumber = incomingNumber;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接通电话
                    try {
                        file = new File(Environment.getExternalStorageDirectory(),
                                incomingNumber + System.currentTimeMillis());
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 声音来源，麦克，说话人
                        mediaRecorder
                                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置音频编码
                        mediaRecorder
                                .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// 设置输出格式
                        mediaRecorder.setOutputFile(file.getAbsolutePath());
                        mediaRecorder.prepare();// 准备录音
                        mediaRecorder.start(); // 开始录音
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:// 挂断电话后回到空闲状态
                    if (mediaRecorder != null) {// mediaRecorder不等于null，停止录音
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;

                        //上传文件到服务器
                        //删除本地文件
                    }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
