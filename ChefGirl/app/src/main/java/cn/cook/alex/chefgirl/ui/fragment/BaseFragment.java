package cn.cook.alex.chefgirl.ui.fragment;

import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import cn.cook.alex.chefgirl.data.RequestManager;
import cn.cook.alex.chefgirl.utils.ToastUtils;

/**
 * Created by alex on 15/2/13.
 */
public class BaseFragment extends Fragment{

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    protected void executeRequest(Request request){
        RequestManager.addRequest(request,this);
    }

    protected Response.ErrorListener errorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showLong(volleyError.getMessage());
            }
        };
    }
}
