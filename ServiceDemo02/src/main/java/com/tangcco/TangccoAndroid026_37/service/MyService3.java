package com.tangcco.TangccoAndroid026_37.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tangcco.TangccoAndroid026_37.binder.DownloadBinder;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class MyService3 extends Service {
    private DownloadBinder downloadBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        downloadBinder = new DownloadBinder(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }
}
