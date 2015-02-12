package cn.cook.alex.chefgirl.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by alex on 15/2/12.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private final Response.Listener<T> mListener;
    private final Map<String ,String > mHeaders;

    public GsonRequest(String url,Class<T> clazz,Response.Listener<T> listener,Response.ErrorListener errorListener){
        this(Method.GET, url, clazz, null, listener, errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz,Map<String ,String > headers,Response.Listener<T> listener,Response.ErrorListener errorListener) {
        super(method, url,errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
        this.mHeaders = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(json,mClazz),HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        }
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);
    }
}
