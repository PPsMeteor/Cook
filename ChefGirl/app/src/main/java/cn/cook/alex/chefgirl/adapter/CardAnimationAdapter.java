package cn.cook.alex.chefgirl.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by alex on 15/2/14.
 */
public class CardAnimationAdapter extends AnimationAdapter {

    private float mTranslationY = 400;

    private float mRotationX = 15;

    private long mDuration = 400;

    public CardAnimationAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @Override
    protected long getAnimationDelayMillis() {
        return 30;
    }

    @Override
    protected long getAnimationDurationMillis() {
        return mDuration;
    }

    @Override
    public Animator[] getAnimators(ViewGroup viewGroup, View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view,"tanslationY",mTranslationY,0),
                ObjectAnimator.ofFloat(view,"rotationX" ,mRotationX,0)
        };
    }
}
