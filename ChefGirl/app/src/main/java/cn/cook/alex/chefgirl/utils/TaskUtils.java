package cn.cook.alex.chefgirl.utils;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by alex on 15/2/13.
 */
public class TaskUtils {
    public static <Params,Progress,Result> void extcuteAsyncTask(AsyncTask<Params,Progress,Result> task,Params... params){
        if (Build.VERSION.SDK_INT > 11){
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,params);
        }else {
            task.execute(params);
        }
    }
}
