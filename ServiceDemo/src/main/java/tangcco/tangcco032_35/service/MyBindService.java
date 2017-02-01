package tangcco.tangcco032_35.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyBindService extends Service {

    private static final String TAG = "MyBindService";

    @Override//绑定
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: 执行");
        return null;
    }

    @Override//创建
    public void onCreate() {
        Log.d(TAG, "onCreate: 执行");
        super.onCreate();
    }

    @Override//解绑定
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: 执行");
        return super.onUnbind(intent);
    }

    @Override//重新绑定
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: 执行");
        super.onRebind(intent);

    }

    @Override//销毁
    public void onDestroy() {
        Log.d(TAG, "onDestroy: 执行");
        super.onDestroy();

    }
}
