package tangcco.tangcco032_31;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {
    private GridView mGridView;
    private String images[] = Contants.IMAGES;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private MyListener myListener = new MyListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        mGridView = (GridView) findViewById(R.id.myGridView);
        //实例化ImageLoader
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
        mGridView.setAdapter(new MyAdapter());

    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.item_layout, parent, false);
                imageView = (ImageView) convertView;
//                convertView.setTag(images[position]);
            } else {
                imageView = (ImageView) convertView;
            }
            mImageLoader.displayImage(images[position], imageView, options, myListener);
            //清除缓存
            //mImageLoader.clearDiskCache();
            return imageView;
        }
    }

    class MyListener extends SimpleImageLoadingListener {
        //如果第一次加载 存到集合中
        List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());


        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean contains = displayedImages.contains(imageUri);
                if (!contains) {
                    //图片淡入的效果
                    FadeInBitmapDisplayer.animate(imageView, 1000);
                    //放入加载过的url
                    displayedImages.add(imageUri);
                }
            }

        }
    }
}
