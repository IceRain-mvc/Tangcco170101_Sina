package tangcco.tangcco032_35.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyBindService01 extends Service {

    private static final String TAG = "MyBindService";

    @Override//绑定
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: 执行");
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public MyBindService01 getService() {
            return MyBindService01.this;

        }
    }

    public int getRandom() {
        //创建一个100以内的随机数
        return new Random().nextInt(100);
    }

}
