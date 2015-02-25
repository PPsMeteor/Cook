package cn.cook.alex.chefgirl.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.etsy.android.grid.StaggeredGridView;

import cn.cook.alex.chefgirl.dao.DBHelper;
import cn.cook.alex.chefgirl.dao.DataProvider;
import cn.cook.alex.chefgirl.utils.LogUtil;

/**
 * Created by alex on 15/2/14.
 */
public class PageStaggeredGridView extends StaggeredGridView implements AbsListView.OnScrollListener {

    private LoadingFooter mLoadingFooter;

    private OnLoadNextListener mLoadNextListener;

    private boolean canLoading = true;

    public PageStaggeredGridView(Context context) {
        super(context);
        init();
    }

    public PageStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public PageStaggeredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        mLoadingFooter = new LoadingFooter(getContext());
        addFooterView(mLoadingFooter.getView());
        setOnScrollListener(this);
    }

    public void setLoadNextListener(OnLoadNextListener loadNextListener) {
        mLoadNextListener = loadNextListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!canLoading) return;

        if (mLoadingFooter.getState() == LoadingFooter.State.Loading
                || mLoadingFooter.getState() == LoadingFooter.State.TheEnd) {
            return;
        }
        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount != 0
                && totalItemCount != getHeaderViewsCount() + getFooterViewsCount()
                && mLoadNextListener != null) {
            mLoadingFooter.setState(LoadingFooter.State.Loading);
            if (mLoadingFooter.getState() != LoadingFooter.State.Loading)
                return;
            mLoadNextListener.onLoadNext();
        }
    }

    public void setState(LoadingFooter.State state) {
        mLoadingFooter.setState(state);
    }

    public void setState(LoadingFooter.State state, long delay) {
        mLoadingFooter.setState(state, delay);
    }

    public boolean isCanLoading() {
        return canLoading;
    }

    public void setCanLoading(boolean canLoading) {
        this.canLoading = canLoading;
    }
}
