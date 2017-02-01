package tangcco.tangcco032_35.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.greenrobot.event.EventBus;
import tangcco.tangcco032_35.entity.MyEvent;
import tangcco.tangcco032_35.utils.HttpUtils;


public class MyIntentService extends IntentService {
    private String urlPath = "http://productphoto.5u588.com/project/big/20145/lvju107360161227.jpg";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override//执行在子线程中
    protected void onHandleIntent(Intent intent) {
        byte[] data = HttpUtils.getData(urlPath);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //发送消息--->EventBus
        EventBus.getDefault().post(new MyEvent(bitmap));
    }


}
