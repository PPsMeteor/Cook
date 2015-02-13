package cn.cook.alex.chefgirl.ui.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import cn.cook.alex.chefgirl.data.RequestManager;
import cn.cook.alex.chefgirl.ui.fragment.BaseFragment;
import cn.cook.alex.chefgirl.utils.ToastUtils;

/**
 * Created by alex on 15/2/13.
 */
public class BaseActivity extends FragmentActivity {
    protected ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    protected void excuteRequest(Request<?> request){
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

    protected void replaceFragment(int viewId,BaseFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(viewId,fragment).commit();
    }
}
