package cn.cook.alex.chefgirl;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.cook.alex.chefgirl.model.Menu;

/**
 * Created by alex on 15/2/12.
 */
public class App extends Application {
    private static Context sContext;
    private static Menu sMenu;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        //可初始化图片加载
        initImageLoader(getApplicationContext());
    }

    private void initImageLoader(Context applicationContext) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(applicationContext)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2*1024*1024))
                .discCacheSize(10*1024*1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    public static Context getContext() {
        return sContext;
    }

    public static Menu getsMenu() {
        return sMenu;
    }

    public static void setsMenu(Menu menu) {
        sMenu = menu;
    }
}
