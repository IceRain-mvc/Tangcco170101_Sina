package com.tangcco.TangccoAndroid026_37.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tangcco.TangccoAndroid026_37.service.MyService4;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class BootComplateReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context, MyService4.class);
        context.startService(in);
    }
}
