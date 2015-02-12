package cn.cook.alex.chefgirl;

import android.app.Application;
import android.content.Context;

/**
 * Created by alex on 15/2/12.
 */
public class App extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        //可初始化图片加载
    }

    public static Context getContext() {
        return sContext;
    }
}
