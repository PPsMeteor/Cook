package cn.cook.alex.chefgirl.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.ui.view.titanic.Titanic;
import cn.cook.alex.chefgirl.ui.view.titanic.TitanicTextView;

/**
 * Created by alex on 15/2/14.
 */
public class LoadingFooter {
    protected View mLoadingFooter;

    @InjectView(R.id.tv_end)
    TextView mLoadingText;

    @InjectView(R.id.tv_titanic)
    TitanicTextView mTitanicText;

    private Titanic titanic;

    protected State mState = State.Idle;

    public static enum State{
        Idle,TheEnd,Loading
    }

    public LoadingFooter(Context context) {
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer,null);
        ButterKnife.inject(this,mLoadingFooter);
        mLoadingFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //屏蔽
            }
        });

        titanic = new Titanic();
        titanic.start(mTitanicText);
    }

    public View getView(){
        return mLoadingFooter;
    }

    public State getState(){
        return mState;
    }

    public void setState(final State state,long delay){
        mLoadingFooter.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        },delay);
    }

    public void setState(State state) {
        if (mState == state){
            return;
        }
        mState = state;

        mLoadingFooter.setVisibility(View.VISIBLE);
        switch (state){
            case Loading:
                mLoadingText.setVisibility(View.GONE);
                mTitanicText.setVisibility(View.VISIBLE);
                break;
            case TheEnd:
                mLoadingText.setVisibility(View.VISIBLE);
                mTitanicText.setVisibility(View.GONE);
                break;
            default:
                mLoadingFooter.setVisibility(View.GONE);
                break;
        }
    }
}
