package cn.cook.alex.chefgirl.utils;

import android.util.Log;

/**
 *
 * Created by alex on 15/1/24.
 */
public class LogUtil {
    public static final void i(Class<?> clazz, String log) {
        String tag = clazz.getSimpleName();
        i(tag, log);
    }

    public static final void v(Class<?> clazz, String log) {
        String tag = clazz.getSimpleName();
        v(tag, log);
    }

    public static final void d(Class<?> clazz, String log) {
        String tag = clazz.getSimpleName();
        d(tag, log);
    }

    public static final void w(Class<?> clazz, String log) {
        String tag = clazz.getSimpleName();
        w(tag, log);
    }

    public static final void e(Class<?> clazz, String log) {
        String tag = clazz.getSimpleName();
        e(tag, log);
    }

    public static final void i(String tag, String log) {
        if (Config.IS_DEBUG) {
            Log.i(tag, log);
        }
    }

    public static final void v(String tag, String log) {
        if (Config.IS_DEBUG) {
            Log.v(tag, log);
        }
    }

    public static final void d(String tag, String log) {
        if (Config.IS_DEBUG) {
            Log.d(tag, log);
        }
    }


    public static final void w(String tag, String log) {
        if (Config.IS_DEBUG) {
            Log.w(tag, log);
        }
    }

    public static final void e(String tag, String log) {
        if (Config.IS_DEBUG) {
            Log.e(tag, log);
        }
    }
}
