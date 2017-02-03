package com.tangcco.TangccoAndroid026_37;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangcco.TangccoAndroid026_37.binder.DownloadBinder;
import com.tangcco.TangccoAndroid026_37.service.MyService1;
import com.tangcco.TangccoAndroid026_37.service.MyService2;


public class MainActivity extends Activity {
    Intent intentService = null;
    MyService2 myService2 = null;
    static TextView tv_time;
    String imageUrl = "http://139.129.130.228:9999/tangcco_server/controller?cmd=4&image=70001";
    ImageView iv_showimage;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            if(bitmap != null){
                iv_showimage.setImageBitmap(bitmap);
            }
        }
    };

    public static TextView getTv_time() {
        return tv_time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_showimage = (ImageView) findViewById(R.id.iv_showimage);
    }


    public void start(View view) {
        intentService = new Intent(this, MyService1.class);
        startService(intentService);
    }

    public void stop(View view) {
        if (intentService != null)
            stopService(intentService);
    }


    public void bind(View view) {
//        intentService = new Intent(this, MyService2.class);
//        bindService(intentService, conn1, BIND_AUTO_CREATE);

        intentService = new Intent();//this, MyService3.class
        intentService.setAction("com.tangcco.service.DOWNLOADSERVCIE");
        bindService(intentService, conn2, BIND_AUTO_CREATE);
    }

    public void unbind(View view) {
        if (conn1 != null) {
            unbindService(conn1);
        }
    }

    ServiceConnection conn1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService2.MyBinder myBinder = (MyService2.MyBinder) service;
            myService2 = myBinder.getMyService2Instance();
//          String currentTime = myService2.getCurrentTime();
//          tv_time.setText("当前时间:" + currentTime);
            myService2.schedule();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService2 = null;
        }
    };

    ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadBinder downloadBinder = (DownloadBinder) service;
            download(downloadBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void download(final DownloadBinder downloadBinder){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = downloadBinder.download(imageUrl);
                    Message msg = handler.obtainMessage();
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
