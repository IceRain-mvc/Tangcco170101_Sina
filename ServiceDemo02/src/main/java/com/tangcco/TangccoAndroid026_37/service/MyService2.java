package com.tangcco.TangccoAndroid026_37.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class MyService2 extends Service {
    private Timer timer;
    private Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        intent = new Intent();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
    }

    /**
     * 服务里面的业务方法
     *
     * @return
     */
    public String getCurrentTime() {
        Time time = new Time();
        time.setToNow();
        String nowtime = time.year + "-" + (time.month + 1) + "-" + time.monthDay + "  " + time.hour + ":" + time.minute + ":" + time.second;
        return nowtime;
    }

    /**
     * 服务里面的业务方法
     *
     * @return
     */
    public void schedule() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendMyBroadcast();
            }
        }, 0, 1000);
    }

    /**
     * 服务里面的业务方法
     *
     * @return
     */
    public void sendMyBroadcast() {
        String currentTime = getCurrentTime();
        intent.putExtra("time",currentTime);
        intent.setAction("com.tangcco.receiver.MYRECEIVER");
        getApplicationContext().sendBroadcast(intent);
    }

    public class MyBinder extends Binder {
        public MyService2 getMyService2Instance() {
            return MyService2.this;
        }
    }
}
