package tangcco.tangcco032_35.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyEvent {

    private Bitmap bitmap;

    public MyEvent(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
