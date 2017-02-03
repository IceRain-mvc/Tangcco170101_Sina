package com.tangcco.TangccoAndroid026_37.binder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;

import java.net.URL;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class DownloadBinder extends Binder {
    private Context context;

    public DownloadBinder(Context context) {
        this.context = context;
    }

    public Bitmap download(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
        return bitmap;
    }

}
