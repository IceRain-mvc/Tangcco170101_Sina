package tangcco.tangcco032_31;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainActivity extends AppCompatActivity {
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        //初始化
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)//图片加载过程中设置的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .showImageForEmptyUri(R.mipmap.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片格式
                .build();
        mImageLoader.displayImage("http://vimg2.ws.126.net/image/snapshot/2015/5/J/P/VAP4I10JP.jpg",
                imageView, options);
    }

    public void click(View view) {
        Intent intent = new Intent(this,GridViewActivity.class);
        startActivity(intent);
    }
}
