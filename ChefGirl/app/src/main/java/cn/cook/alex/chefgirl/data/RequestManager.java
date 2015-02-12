package cn.cook.alex.chefgirl.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import cn.cook.alex.chefgirl.App;

/**
 * Created by alex on 15/2/12.
 */
public class RequestManager {
    public static RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());

    public RequestManager() {
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null)
            request.setTag(tag);
        requestQueue.add(request);
    }

    public static void cancelAll(Object tag)
    {
        requestQueue.cancelAll(tag);
    }
}
