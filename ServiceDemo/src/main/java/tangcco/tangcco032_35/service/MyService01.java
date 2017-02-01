package tangcco.tangcco032_35.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tangcco.tangcco032_35.R;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyService01 extends Service {
    private static final String TAG = "TAG";

    @Override
    public void onCreate() {//只是在被创建的之后执行
        super.onCreate();
        Log.d(TAG, "onCreate: 执行");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override//每次开启服务的时候执行
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: 执行");
        String hello = intent.getStringExtra("hello");
        Log.d(TAG, "onStartCommand: " + hello);
        Toast.makeText(this, "hello" + hello, Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {
                List<Bitmap> bitmaps = new ArrayList<Bitmap>();
                while (true) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    bitmaps.add(bitmap);
                }
            }
        }.start();

        /**
         *  START_STICKY：（常量值：1）车祸后自己苏醒，但是失忆；
            START_NOT_STICKY：（常量值：2）车祸后再也没有苏醒；
            START_REDELIVER_INTENT：（常量值：3）车祸后自己苏醒，依然保持记忆。
         */
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 执行");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }


}
