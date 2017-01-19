package tangcco.jcplayer.app;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }
    private void initImageLoader() {
        //进行ImageLoader配置
        //创建系统默认的配置
//        ImageLoaderConfiguration configuration =
//                ImageLoaderConfiguration.createDefault(this);
        //缓存的目录
        File cacheDir = new File(Environment.
                getExternalStorageDirectory()
                .getPath() + "/0032/cache");
        //自定义配置
        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration.Builder(this)
                        .threadPoolSize(4)//设置线程池的个数
                        .diskCache(new UnlimitedDiskCache(cacheDir))//设置缓存目录
                        .diskCacheSize(10 * 1024 * 1024)//设置缓存目录的大小
                        .diskCacheFileNameGenerator(new Md5FileNameGenerator())//MD5加密
                        .diskCacheFileCount(20)
//                        .diskCacheExtraOptions()
                        .build();
        //初始化配置信息
        ImageLoader.getInstance().init(configuration);
    }
}
