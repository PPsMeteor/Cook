package cn.cook.alex.chefgirl.data;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import cn.cook.alex.chefgirl.App;

/**
 * Created by alex on 15/2/12.
 */
public class ImageCacheManager {
    // 取运行内存阈值的1/8作为图片缓存
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) App.getContext()
            .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;

    private static ImageLoader imageLoader = new ImageLoader(RequestManager.requestQueue, new BitmapLruCache(MEM_CACHE_SIZE));

    public ImageCacheManager() {
    }

//    加载监听
    public static ImageLoader.ImageListener getImageListener(final ImageView imageView, final Drawable defaultImageDrawable, final Drawable errorImageDrawable) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null){
                    if (!b && defaultImageDrawable != null){
                        TransitionDrawable transitionDrawable = new TransitionDrawable(
                                new Drawable[]{
                                        defaultImageDrawable,
                                        new BitmapDrawable(App.getContext().getResources(),imageContainer.getBitmap())
                                }
                        );
                        transitionDrawable.setCrossFadeEnabled(true);//图像渐变的效果
                        imageView.setImageDrawable(transitionDrawable);
                        transitionDrawable.startTransition(150);
                    }else{
                        imageView.setImageBitmap(imageContainer.getBitmap());
                    }
                }else if (defaultImageDrawable != null){
                    imageView.setImageDrawable(defaultImageDrawable);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (errorImageDrawable != null)
                    imageView.setImageDrawable(errorImageDrawable);
            }
        };
    }

    //加载图片
    public static ImageLoader.ImageContainer loadImage(String requestUrl,ImageLoader.ImageListener imageListener) {
        return loadImage(requestUrl,imageListener,0,0);
    }

    public static ImageLoader.ImageContainer loadImage(String requestUrl,ImageLoader.ImageListener imageListener,int maxWidth,int maxHeight){
        return imageLoader.get(requestUrl,imageListener,maxWidth,maxHeight);
    }
}
