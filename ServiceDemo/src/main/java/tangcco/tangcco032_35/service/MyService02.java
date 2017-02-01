package tangcco.tangcco032_35.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import tangcco.tangcco032_35.utils.HttpUtils;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyService02 extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String imgUrl = intent.getStringExtra("imgUrl");
        //下载
        new Thread() {
            @Override
            public void run() {
                //
                byte[] data = HttpUtils.getData(imgUrl);
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction("Succeed");
                intentBroadcast.putExtra("data", data);
                sendBroadcast(intentBroadcast);
            }
        }.start();

        //自杀
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

}
