package tangcco.tangcco032_35;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;
import tangcco.tangcco032_35.entity.MyEvent;
import tangcco.tangcco032_35.service.MyBindService02;
import tangcco.tangcco032_35.service.MyIntentService;
import tangcco.tangcco032_35.service.MyService02;

public class MainActivity extends AppCompatActivity {

    private Button btn_start, btn_stop, btn_event;
    private Intent intentService;
    private String urlPath = "http://e.hiphotos.baidu.com/zhidao/pic/item/730e0cf3d7ca7bcb96e256f5bc096b63f724a84b.jpg";
    private MyBroadcastReceiver myBroadcastReceiver;
    private ImageView imageView;
    private MyConn myConn;

    private MyBindService02.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_event = (Button) findViewById(R.id.btn_event);
        imageView = (ImageView) findViewById(R.id.imageView);

        //注册
        EventBus.getDefault().register(this);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启服务
//                intentService = new Intent(MainActivity.this, MyService01.class);
//                intentService.putExtra("hello", "前世今生");
//                startService(intentService);
                intentService = new Intent(MainActivity.this, MyService02.class);
                intentService.putExtra("imgUrl", urlPath);
                startService(intentService);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentService != null) {
                    stopService(intentService);
                }

            }
        });


        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentService = new Intent(MainActivity.this, MyIntentService.class);
                startService(intentService);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Succeed");
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myBroadcastReceiver != null) {
            unregisterReceiver(myBroadcastReceiver);

        }
    }

    /**
     * 接收EventBus 发送过来的消息 订阅消息
     *
     * @param event
     */
    public void onEventMainThread(MyEvent event) {
        Bitmap bitmap = event.getBitmap();
        imageView.setImageBitmap(bitmap);
    }

    public void onEvent(MyEvent event) {

    }

    public void onEventAsync() {

    }

    public void onEventBackgroundThread() {

    }

    public void bindService(View view) {
        myConn = new MyConn();
        //绑定服务
        intentService = new Intent(this, MyBindService02.class);
        bindService(intentService, myConn, BIND_AUTO_CREATE);
    }

    public void unBind(View view) {
        if (myConn != null) {
            //接触绑定
            unbindService(myConn);
        }
    }

    public void random(View view) {
        //获取随机数
//        int random = myBinder.getService().getRandom();
//        Toast.makeText(this, "随机数:" + random, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myConn != null) {
            unbindService(myConn);
        }
    }

    public void start(View view) {
        if (myConn != null) {
            myBinder.play();
        }
    }
    public void pause(View view) {
        if (myConn != null) {
            myBinder.pause();
        }
    }
    public void stop(View view) {
        if (myConn != null) {
            myBinder.stop();
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

               @Override
        public void onReceive(Context context, Intent intent) {
            //40kb
            byte[] datas = intent.getByteArrayExtra("data");
            Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);

            imageView.setImageBitmap(bitmap);

        }
    }

    class MyConn implements ServiceConnection {
        // 当与服务建立了连接的时候,调用该方法.
        // name:组件名.指的就是建立了连接的service.
        // service:service与其他组件进行通信的一个频道.
        @Override//连接时候调用
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("MyBindService", "onServiceConnected: ");
            myBinder = (MyBindService02.MyBinder) service;
        }

        @Override//一般情况下 程序 kill掉了 会执行此方法
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MyBindService", "onServiceDisconnected: ");
        }
    }

}

