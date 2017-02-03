package com.tangcco.TangccoAndroid026_37.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tangcco.TangccoAndroid026_37.MainActivity;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if("com.tangcco.receiver.MYRECEIVER".equals(intent.getAction())){
            String time = intent.getStringExtra("time");
            MainActivity.getTv_time().setText("当前时间:" + time);

        }
    }
}
