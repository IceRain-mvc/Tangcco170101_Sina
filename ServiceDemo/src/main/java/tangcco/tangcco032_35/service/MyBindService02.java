package tangcco.tangcco032_35.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import tangcco.tangcco032_35.R;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyBindService02 extends Service {

    private MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.ai);
    }

    @Override//绑定
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void play() {
            if (player != null) {
                player.start();
            }
        }

        public void pause() {
            if (player != null) {
                player.pause();
            }
        }

        public void stop() {
            if (player != null) {
                player.stop();
            }
        }
    }


}
